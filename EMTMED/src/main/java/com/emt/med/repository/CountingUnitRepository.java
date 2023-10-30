package com.emt.med.repository;

import com.emt.med.domain.CountingUnit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CountingUnit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CountingUnitRepository extends JpaRepository<CountingUnit, Long> {}
