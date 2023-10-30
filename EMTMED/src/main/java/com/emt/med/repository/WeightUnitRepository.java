package com.emt.med.repository;

import com.emt.med.domain.WeightUnit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the WeightUnit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WeightUnitRepository extends JpaRepository<WeightUnit, Long> {}
