package org.waybill.account.management.database.specification;

import jakarta.persistence.criteria.*;
import org.waybill.account.management.database.entity.Account;
import org.waybill.account.management.database.entity.Contact;
import org.waybill.account.management.database.entity.User;
import org.waybill.account.management.database.entity.UserContact;
import org.waybill.account.management.dto.UserContactFilter;
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
                .add(criteria.getExternalUserId(), externalUserId -> builder.equal(userJoin.get("externalUserId"), externalUserId))
                .add(criteria.getRole(), role -> builder.equal(userJoin.get("role"), role))
                .add(criteria.getAccountId(), accountId -> builder.equal(accountJoin.get("id"), accountId))
                .add(criteria.getFirstName(), firstName -> builder.like(builder.lower(contactJoin.get("firstName")), "%" + firstName.toLowerCase() + "%"))
                .add(criteria.getLastName(), lastName -> builder.like(builder.lower(contactJoin.get("lastName")), "%" + lastName.toLowerCase() + "%"))
                .add(criteria.getPhoneNumber(), phoneNumber -> builder.equal(contactJoin.get("phoneNumber"), phoneNumber))
                .build();
        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
