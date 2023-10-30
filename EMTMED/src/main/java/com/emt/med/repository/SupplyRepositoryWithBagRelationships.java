package com.emt.med.repository;

import com.emt.med.domain.Supply;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface SupplyRepositoryWithBagRelationships {
    Optional<Supply> fetchBagRelationships(Optional<Supply> supply);

    List<Supply> fetchBagRelationships(List<Supply> supplies);

    Page<Supply> fetchBagRelationships(Page<Supply> supplies);
}
