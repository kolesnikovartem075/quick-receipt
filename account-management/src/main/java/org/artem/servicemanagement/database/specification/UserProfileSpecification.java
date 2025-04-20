package org.artem.servicemanagement.database.specification;

import jakarta.persistence.criteria.*;
import org.artem.servicemanagement.database.entity.User;
import org.artem.servicemanagement.database.entity.UserContactProfile;
import org.artem.servicemanagement.dto.UserProfileFilter;
import org.springframework.data.jpa.domain.Specification;

public class UserProfileSpecification implements Specification<UserContactProfile> {


    private final UserProfileFilter criteria;

    public UserProfileSpecification(UserProfileFilter criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<UserContactProfile> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        Join<UserContactProfile, User> userJoin = root.join("user", JoinType.INNER);

        var predicates = SPredicates.builder()
                .add(criteria.firstName(), firstName -> builder.like(root.get("firstName"), firstName))
                .add(criteria.lastName(), lastName -> builder.like(root.get("lastName"), lastName))
                .add(criteria.phoneNumber(), phoneNumber -> builder.equal(root.get("phoneNumber"), phoneNumber))
                .add(criteria.externalUserId(), externalUserId -> builder.equal(userJoin.get("externalUserId"), externalUserId))
                .build();

        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
