package com.emt.med.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.emt.med.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WeightUnitDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WeightUnitDTO.class);
        WeightUnitDTO weightUnitDTO1 = new WeightUnitDTO();
        weightUnitDTO1.setId(1L);
        WeightUnitDTO weightUnitDTO2 = new WeightUnitDTO();
        assertThat(weightUnitDTO1).isNotEqualTo(weightUnitDTO2);
        weightUnitDTO2.setId(weightUnitDTO1.getId());
        assertThat(weightUnitDTO1).isEqualTo(weightUnitDTO2);
        weightUnitDTO2.setId(2L);
        assertThat(weightUnitDTO1).isNotEqualTo(weightUnitDTO2);
        weightUnitDTO1.setId(null);
        assertThat(weightUnitDTO1).isNotEqualTo(weightUnitDTO2);
    }
}
