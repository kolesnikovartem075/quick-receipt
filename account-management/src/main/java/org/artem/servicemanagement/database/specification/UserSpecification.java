package org.artem.servicemanagement.database.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.artem.servicemanagement.database.entity.User;
import org.artem.servicemanagement.dto.UserFilter;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification implements Specification<User> {


    private final UserFilter criteria;

    public UserSpecification(UserFilter criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        var predicates = SPredicates.builder()
                .add(criteria.externalUserId(), externalUserId -> builder.equal(root.get("externalUserId"), externalUserId))
                .build();

        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
