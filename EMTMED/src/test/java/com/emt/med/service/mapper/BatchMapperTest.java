package com.emt.med.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BatchMapperTest {

    private BatchMapper batchMapper;

    @BeforeEach
    public void setUp() {
        batchMapper = new BatchMapperImpl();
    }
}
