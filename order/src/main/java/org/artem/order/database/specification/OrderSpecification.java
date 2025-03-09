package org.artem.order.database.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.artem.order.dto.OrderFilter;
import org.artem.order.database.entity.Order;
import org.springframework.data.jpa.domain.Specification;

public class OrderSpecification implements Specification<Order> {


    private final OrderFilter criteria;

    public OrderSpecification(OrderFilter criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        var predicates = SPredicates.builder()
                .add(criteria.serviceId(), id -> builder.equal(root.get("serviceId"), id))
                .add(criteria.userId(), id -> builder.equal(root.get("userId"), id))
                .add(criteria.status(), status -> builder.equal(root.get("status"), status))
                .add(criteria.dateCreated(), date -> builder.greaterThanOrEqualTo(root.get("dateCreated"), date))
                .build();

        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
