package org.artem.servicemanagement.database.specification;

import jakarta.persistence.criteria.*;
import org.artem.servicemanagement.database.entity.Account;
import org.artem.servicemanagement.database.entity.Contact;
import org.artem.servicemanagement.database.entity.User;
import org.artem.servicemanagement.database.entity.UserContact;
import org.artem.servicemanagement.dto.UserContactFilter;
import org.springframework.data.jpa.domain.Specification;

public class UserContactSpecification implements Specification<UserContact> {


    private final UserContactFilter criteria;

    public UserContactSpecification(UserContactFilter criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<UserContact> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        Join<UserContact, User> userJoin = root.join("user", JoinType.INNER);
        Join<UserContact, Contact> contactJoin = root.join("contact", JoinType.INNER);
        Join<User, Account> accountJoin = userJoin.join("account", JoinType.INNER);

        var predicates = SPredicates.builder()
                .add(criteria.externalUserId(), externalUserId -> builder.equal(userJoin.get("externalUserId"), externalUserId))
                .add(criteria.role(), role -> builder.equal(userJoin.get("role"), role))
                .add(criteria.accountId(), accountId -> builder.equal(accountJoin.get("id"), accountId))
                .add(criteria.firstName(), firstName -> builder.like(builder.lower(contactJoin.get("firstName")), "%" + firstName.toLowerCase() + "%"))
                .add(criteria.lastName(), lastName -> builder.like(builder.lower(contactJoin.get("lastName")), "%" + lastName.toLowerCase() + "%"))
                .add(criteria.phoneNumber(), phoneNumber -> builder.equal(contactJoin.get("phoneNumber"), phoneNumber))
                .build();
        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
