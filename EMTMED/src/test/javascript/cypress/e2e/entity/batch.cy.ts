import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('Batch e2e test', () => {
  const batchPageUrl = '/emtmed/batch';
  const batchPageUrlPattern = new RegExp('/emtmed/batch(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const batchSample = {};

  let batch;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/emtmed/api/batches+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/emtmed/api/batches').as('postEntityRequest');
    cy.intercept('DELETE', '/services/emtmed/api/batches/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (batch) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/emtmed/api/batches/${batch.id}`,
      }).then(() => {
        batch = undefined;
      });
    }
  });

  it('Batches menu should load Batches page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('emtmed/batch');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Batch').should('exist');
    cy.url().should('match', batchPageUrlPattern);
  });

  describe('Batch page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(batchPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Batch page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/emtmed/batch/new$'));
        cy.getEntityCreateUpdateHeading('Batch');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', batchPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/emtmed/api/batches',
          body: batchSample,
        }).then(({ body }) => {
          batch = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/emtmed/api/batches+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [batch],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(batchPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Batch page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('batch');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', batchPageUrlPattern);
      });

      it('edit button click should load edit Batch page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Batch');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', batchPageUrlPattern);
      });

      it('edit button click should load edit Batch page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Batch');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', batchPageUrlPattern);
      });

      it('last delete button click should delete instance of Batch', () => {
        cy.intercept('GET', '/services/emtmed/api/batches/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('batch').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', batchPageUrlPattern);

        batch = undefined;
      });
    });
  });

  describe('new Batch page', () => {
    beforeEach(() => {
      cy.visit(`${batchPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Batch');
    });

    it('should create an instance of Batch', () => {
      cy.get(`[data-cy="manufacturer"]`).type('Bebes Metical').should('have.value', 'Bebes Metical');

      cy.get(`[data-cy="administrationRoute"]`).type('Soluciones Raton Puente').should('have.value', 'Soluciones Raton Puente');

      cy.get(`[data-cy="expirationDate"]`).type('2023-10-29').blur().should('have.value', '2023-10-29');

      cy.get(`[data-cy="status"]`).select('RED');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        batch = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', batchPageUrlPattern);
    });
  });
});
