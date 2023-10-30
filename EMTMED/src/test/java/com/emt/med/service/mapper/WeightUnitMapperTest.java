package com.emt.med.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WeightUnitMapperTest {

    private WeightUnitMapper weightUnitMapper;

    @BeforeEach
    public void setUp() {
        weightUnitMapper = new WeightUnitMapperImpl();
    }
}
