package org.artem.servicemanagement.database.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.artem.servicemanagement.database.entity.Service;
import org.artem.servicemanagement.dto.ServiceFilter;
import org.springframework.data.jpa.domain.Specification;

public class ServiceSpecification implements Specification<Service> {


    private final ServiceFilter criteria;

    public ServiceSpecification(ServiceFilter criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Service> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        var predicates = SPredicates.builder()
//                .add(criteria.accountId(), id -> builder.equal(root.get("accountId"), id))
                .build();

        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
