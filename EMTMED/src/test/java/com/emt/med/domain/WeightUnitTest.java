package com.emt.med.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.emt.med.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WeightUnitTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WeightUnit.class);
        WeightUnit weightUnit1 = new WeightUnit();
        weightUnit1.setId(1L);
        WeightUnit weightUnit2 = new WeightUnit();
        weightUnit2.setId(weightUnit1.getId());
        assertThat(weightUnit1).isEqualTo(weightUnit2);
        weightUnit2.setId(2L);
        assertThat(weightUnit1).isNotEqualTo(weightUnit2);
        weightUnit1.setId(null);
        assertThat(weightUnit1).isNotEqualTo(weightUnit2);
    }
}
