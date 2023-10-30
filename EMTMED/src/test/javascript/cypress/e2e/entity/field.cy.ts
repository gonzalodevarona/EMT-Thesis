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

describe('Field e2e test', () => {
  const fieldPageUrl = '/emtmed/field';
  const fieldPageUrlPattern = new RegExp('/emtmed/field(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const fieldSample = {};

  let field;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/emtmed/api/fields+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/emtmed/api/fields').as('postEntityRequest');
    cy.intercept('DELETE', '/services/emtmed/api/fields/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (field) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/emtmed/api/fields/${field.id}`,
      }).then(() => {
        field = undefined;
      });
    }
  });

  it('Fields menu should load Fields page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('emtmed/field');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Field').should('exist');
    cy.url().should('match', fieldPageUrlPattern);
  });

  describe('Field page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(fieldPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Field page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/emtmed/field/new$'));
        cy.getEntityCreateUpdateHeading('Field');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', fieldPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/emtmed/api/fields',
          body: fieldSample,
        }).then(({ body }) => {
          field = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/emtmed/api/fields+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [field],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(fieldPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Field page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('field');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', fieldPageUrlPattern);
      });

      it('edit button click should load edit Field page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Field');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', fieldPageUrlPattern);
      });

      it('edit button click should load edit Field page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Field');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', fieldPageUrlPattern);
      });

      it('last delete button click should delete instance of Field', () => {
        cy.intercept('GET', '/services/emtmed/api/fields/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('field').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', fieldPageUrlPattern);

        field = undefined;
      });
    });
  });

  describe('new Field page', () => {
    beforeEach(() => {
      cy.visit(`${fieldPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Field');
    });

    it('should create an instance of Field', () => {
      cy.get(`[data-cy="value"]`).type('invoice Queso Extremadura').should('have.value', 'invoice Queso Extremadura');

      cy.get(`[data-cy="name"]`).type('monitorizar Pequeño').should('have.value', 'monitorizar Pequeño');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        field = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', fieldPageUrlPattern);
    });
  });
});
