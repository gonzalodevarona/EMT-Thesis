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

describe('WeightUnit e2e test', () => {
  const weightUnitPageUrl = '/emtmed/weight-unit';
  const weightUnitPageUrlPattern = new RegExp('/emtmed/weight-unit(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const weightUnitSample = {};

  let weightUnit;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/emtmed/api/weight-units+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/emtmed/api/weight-units').as('postEntityRequest');
    cy.intercept('DELETE', '/services/emtmed/api/weight-units/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (weightUnit) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/emtmed/api/weight-units/${weightUnit.id}`,
      }).then(() => {
        weightUnit = undefined;
      });
    }
  });

  it('WeightUnits menu should load WeightUnits page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('emtmed/weight-unit');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('WeightUnit').should('exist');
    cy.url().should('match', weightUnitPageUrlPattern);
  });

  describe('WeightUnit page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(weightUnitPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create WeightUnit page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/emtmed/weight-unit/new$'));
        cy.getEntityCreateUpdateHeading('WeightUnit');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', weightUnitPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/emtmed/api/weight-units',
          body: weightUnitSample,
        }).then(({ body }) => {
          weightUnit = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/emtmed/api/weight-units+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [weightUnit],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(weightUnitPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details WeightUnit page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('weightUnit');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', weightUnitPageUrlPattern);
      });

      it('edit button click should load edit WeightUnit page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('WeightUnit');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', weightUnitPageUrlPattern);
      });

      it('edit button click should load edit WeightUnit page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('WeightUnit');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', weightUnitPageUrlPattern);
      });

      it('last delete button click should delete instance of WeightUnit', () => {
        cy.intercept('GET', '/services/emtmed/api/weight-units/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('weightUnit').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', weightUnitPageUrlPattern);

        weightUnit = undefined;
      });
    });
  });

  describe('new WeightUnit page', () => {
    beforeEach(() => {
      cy.visit(`${weightUnitPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('WeightUnit');
    });

    it('should create an instance of WeightUnit', () => {
      cy.get(`[data-cy="name"]`).type('conjunto').should('have.value', 'conjunto');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        weightUnit = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', weightUnitPageUrlPattern);
    });
  });
});
