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

describe('CountingUnit e2e test', () => {
  const countingUnitPageUrl = '/emtmed/counting-unit';
  const countingUnitPageUrlPattern = new RegExp('/emtmed/counting-unit(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const countingUnitSample = {};

  let countingUnit;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/emtmed/api/counting-units+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/emtmed/api/counting-units').as('postEntityRequest');
    cy.intercept('DELETE', '/services/emtmed/api/counting-units/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (countingUnit) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/emtmed/api/counting-units/${countingUnit.id}`,
      }).then(() => {
        countingUnit = undefined;
      });
    }
  });

  it('CountingUnits menu should load CountingUnits page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('emtmed/counting-unit');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('CountingUnit').should('exist');
    cy.url().should('match', countingUnitPageUrlPattern);
  });

  describe('CountingUnit page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(countingUnitPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create CountingUnit page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/emtmed/counting-unit/new$'));
        cy.getEntityCreateUpdateHeading('CountingUnit');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', countingUnitPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/emtmed/api/counting-units',
          body: countingUnitSample,
        }).then(({ body }) => {
          countingUnit = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/emtmed/api/counting-units+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [countingUnit],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(countingUnitPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details CountingUnit page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('countingUnit');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', countingUnitPageUrlPattern);
      });

      it('edit button click should load edit CountingUnit page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CountingUnit');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', countingUnitPageUrlPattern);
      });

      it('edit button click should load edit CountingUnit page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CountingUnit');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', countingUnitPageUrlPattern);
      });

      it('last delete button click should delete instance of CountingUnit', () => {
        cy.intercept('GET', '/services/emtmed/api/counting-units/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('countingUnit').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', countingUnitPageUrlPattern);

        countingUnit = undefined;
      });
    });
  });

  describe('new CountingUnit page', () => {
    beforeEach(() => {
      cy.visit(`${countingUnitPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('CountingUnit');
    });

    it('should create an instance of CountingUnit', () => {
      cy.get(`[data-cy="name"]`).type('panel global').should('have.value', 'panel global');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        countingUnit = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', countingUnitPageUrlPattern);
    });
  });
});
