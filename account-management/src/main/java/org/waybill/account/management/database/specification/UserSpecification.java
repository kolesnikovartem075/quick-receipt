package org.waybill.account.management.database.specification;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.waybill.account.management.database.entity.Account;
import org.waybill.account.management.database.entity.User;
import org.waybill.account.management.dto.UserFilter;

public class UserSpecification implements Specification<User> {


    private final UserFilter criteria;

    public UserSpecification(UserFilter criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        Join<User, Account> userJoin = root.join("account", JoinType.INNER);

        var predicates = SPredicates.builder()
                .add(criteria.externalUserId(), externalUserId -> builder.equal(root.get("externalUserId"), externalUserId))
                .add(criteria.role(), role -> builder.equal(root.get("role"), role))
                .add(criteria.accountId(), accountId -> builder.equal(userJoin.get("accountId"), accountId))
                .build();

        return builder.and(predicates.toArray(new Predicate[0]));
    }
}