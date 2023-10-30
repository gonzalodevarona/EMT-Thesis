package com.emt.med.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.emt.med.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CountingUnitTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CountingUnit.class);
        CountingUnit countingUnit1 = new CountingUnit();
        countingUnit1.setId(1L);
        CountingUnit countingUnit2 = new CountingUnit();
        countingUnit2.setId(countingUnit1.getId());
        assertThat(countingUnit1).isEqualTo(countingUnit2);
        countingUnit2.setId(2L);
        assertThat(countingUnit1).isNotEqualTo(countingUnit2);
        countingUnit1.setId(null);
        assertThat(countingUnit1).isNotEqualTo(countingUnit2);
    }
}
