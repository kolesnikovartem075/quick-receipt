package org.waybill.order.database.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.waybill.order.dto.OrderFilter;
import org.waybill.order.database.entity.Order;
import org.springframework.data.jpa.domain.Specification;

public class OrderSpecification implements Specification<Order> {


    private final OrderFilter criteria;

    public OrderSpecification(OrderFilter criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        var predicates = SPredicates.builder()
                .add(criteria.accountId(), id -> builder.equal(root.get("accountId"), id))
                .add(criteria.userId(), id -> builder.equal(root.get("userId"), id))
                .add(criteria.status(), status -> builder.equal(root.get("status"), status))
                .add(criteria.dateCreated(), date -> builder.greaterThanOrEqualTo(root.get("dateCreated"), date))
                .build();

        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
