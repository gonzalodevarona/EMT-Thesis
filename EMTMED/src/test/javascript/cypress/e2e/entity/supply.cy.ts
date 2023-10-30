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

describe('Supply e2e test', () => {
  const supplyPageUrl = '/emtmed/supply';
  const supplyPageUrlPattern = new RegExp('/emtmed/supply(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const supplySample = {};

  let supply;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/emtmed/api/supplies+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/emtmed/api/supplies').as('postEntityRequest');
    cy.intercept('DELETE', '/services/emtmed/api/supplies/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (supply) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/emtmed/api/supplies/${supply.id}`,
      }).then(() => {
        supply = undefined;
      });
    }
  });

  it('Supplies menu should load Supplies page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('emtmed/supply');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Supply').should('exist');
    cy.url().should('match', supplyPageUrlPattern);
  });

  describe('Supply page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(supplyPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Supply page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/emtmed/supply/new$'));
        cy.getEntityCreateUpdateHeading('Supply');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', supplyPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/emtmed/api/supplies',
          body: supplySample,
        }).then(({ body }) => {
          supply = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/emtmed/api/supplies+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [supply],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(supplyPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Supply page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('supply');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', supplyPageUrlPattern);
      });

      it('edit button click should load edit Supply page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Supply');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', supplyPageUrlPattern);
      });

      it('edit button click should load edit Supply page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Supply');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', supplyPageUrlPattern);
      });

      it('last delete button click should delete instance of Supply', () => {
        cy.intercept('GET', '/services/emtmed/api/supplies/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('supply').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', supplyPageUrlPattern);

        supply = undefined;
      });
    });
  });

  describe('new Supply page', () => {
    beforeEach(() => {
      cy.visit(`${supplyPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Supply');
    });

    it('should create an instance of Supply', () => {
      cy.get(`[data-cy="name"]`).type('open-source Subida Guapa').should('have.value', 'open-source Subida Guapa');

      cy.get(`[data-cy="weight"]`).type('62593').should('have.value', '62593');

      cy.get(`[data-cy="quantity"]`).type('88521').should('have.value', '88521');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        supply = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', supplyPageUrlPattern);
    });
  });
});
