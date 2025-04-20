package org.artem.servicemanagement.database.specification;

import jakarta.persistence.criteria.*;
import org.artem.servicemanagement.database.entity.Account;
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
        Join<Account, User> userJoin = root.join("account", JoinType.INNER);

        var predicates = SPredicates.builder()
                .add(criteria.externalUserId(), externalUserId -> builder.equal(root.get("externalUserId"), externalUserId))
                .add(criteria.role(), role -> builder.equal(root.get("role"), role))
                .add(criteria.accountId(), accountId -> builder.equal(userJoin.get("accountId"), accountId))
                .build();

        return builder.and(predicates.toArray(new Predicate[0]));
    }
}