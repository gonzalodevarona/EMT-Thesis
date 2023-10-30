package com.emt.med.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.emt.med.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CountingUnitDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CountingUnitDTO.class);
        CountingUnitDTO countingUnitDTO1 = new CountingUnitDTO();
        countingUnitDTO1.setId(1L);
        CountingUnitDTO countingUnitDTO2 = new CountingUnitDTO();
        assertThat(countingUnitDTO1).isNotEqualTo(countingUnitDTO2);
        countingUnitDTO2.setId(countingUnitDTO1.getId());
        assertThat(countingUnitDTO1).isEqualTo(countingUnitDTO2);
        countingUnitDTO2.setId(2L);
        assertThat(countingUnitDTO1).isNotEqualTo(countingUnitDTO2);
        countingUnitDTO1.setId(null);
        assertThat(countingUnitDTO1).isNotEqualTo(countingUnitDTO2);
    }
}
