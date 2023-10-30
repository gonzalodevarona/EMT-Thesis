package com.emt.med.repository;

import com.emt.med.domain.MedicationBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicationBatchRepository extends JpaRepository<MedicationBatch, Long> {
}
