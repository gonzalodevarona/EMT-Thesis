package com.emt.med.repository;

import com.emt.med.domain.Supply;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Supply entity.
 *
 * When extending this class, extend SupplyRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface SupplyRepository extends SupplyRepositoryWithBagRelationships, JpaRepository<Supply, Long> {
    default Optional<Supply> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findOneWithToOneRelationships(id));
    }

    default List<Supply> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships());
    }

    default Page<Supply> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships(pageable));
    }

    @Query(
        value = "select distinct supply from Supply supply left join fetch supply.location left join fetch supply.weightUnit left join fetch supply.countingUnit",
        countQuery = "select count(distinct supply) from Supply supply"
    )
    Page<Supply> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct supply from Supply supply left join fetch supply.location left join fetch supply.weightUnit left join fetch supply.countingUnit"
    )
    List<Supply> findAllWithToOneRelationships();

    @Query(
        "select supply from Supply supply left join fetch supply.location left join fetch supply.weightUnit left join fetch supply.countingUnit where supply.id =:id"
    )
    Optional<Supply> findOneWithToOneRelationships(@Param("id") Long id);
}
