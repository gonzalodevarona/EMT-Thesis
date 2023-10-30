package com.emt.med.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CountingUnitMapperTest {

    private CountingUnitMapper countingUnitMapper;

    @BeforeEach
    public void setUp() {
        countingUnitMapper = new CountingUnitMapperImpl();
    }
}
