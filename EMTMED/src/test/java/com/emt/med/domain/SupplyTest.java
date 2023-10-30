package com.emt.med.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.emt.med.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SupplyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Supply.class);
        Supply supply1 = new Supply();
        supply1.setId(1L);
        Supply supply2 = new Supply();
        supply2.setId(supply1.getId());
        assertThat(supply1).isEqualTo(supply2);
        supply2.setId(2L);
        assertThat(supply1).isNotEqualTo(supply2);
        supply1.setId(null);
        assertThat(supply1).isNotEqualTo(supply2);
    }
}
