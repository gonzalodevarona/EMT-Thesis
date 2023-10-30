package com.emt.med.repository;

import com.emt.med.domain.Supply;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class SupplyRepositoryWithBagRelationshipsImpl implements SupplyRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Supply> fetchBagRelationships(Optional<Supply> supply) {
        return supply.map(this::fetchFields);
    }

    @Override
    public Page<Supply> fetchBagRelationships(Page<Supply> supplies) {
        return new PageImpl<>(fetchBagRelationships(supplies.getContent()), supplies.getPageable(), supplies.getTotalElements());
    }

    @Override
    public List<Supply> fetchBagRelationships(List<Supply> supplies) {
        return Optional.of(supplies).map(this::fetchFields).orElse(Collections.emptyList());
    }

    Supply fetchFields(Supply result) {
        return entityManager
            .createQuery("select supply from Supply supply left join fetch supply.fields where supply is :supply", Supply.class)
            .setParameter("supply", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Supply> fetchFields(List<Supply> supplies) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, supplies.size()).forEach(index -> order.put(supplies.get(index).getId(), index));
        List<Supply> result = entityManager
            .createQuery("select distinct supply from Supply supply left join fetch supply.fields where supply in :supplies", Supply.class)
            .setParameter("supplies", supplies)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
