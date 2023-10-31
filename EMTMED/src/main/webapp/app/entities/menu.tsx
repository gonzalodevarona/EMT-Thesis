import React, { useEffect, useState } from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';
import { addTranslationSourcePrefix } from 'app/shared/reducers/locale';
import { useAppDispatch, useAppSelector } from 'app/config/store';

const EntitiesMenu = () => {
  const lastChange = useAppSelector(state => state.locale.lastChange);
  const dispatch = useAppDispatch();
  useEffect(() => {
    dispatch(addTranslationSourcePrefix('services/emtmed/'));
  }, [lastChange]);

  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/emtmed/weight-unit">
        <Translate contentKey="global.menu.entities.emtmedWeightUnit" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/emtmed/supply">
        <Translate contentKey="global.menu.entities.emtmedSupply" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/emtmed/field">
        <Translate contentKey="global.menu.entities.emtmedField" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/emtmed/location">
        <Translate contentKey="global.menu.entities.emtmedLocation" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/emtmed/counting-unit">
        <Translate contentKey="global.menu.entities.emtmedCountingUnit" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/emtmed/batch">
        <Translate contentKey="global.menu.entities.emtmedBatch" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/emtmed/order">
        <Translate contentKey="global.menu.entities.emtmedOrder" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/emtmed/medication-batch">
        <Translate contentKey="global.menu.entities.emtmedMedicationBatch" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
