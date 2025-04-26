package org.waybill.account.management.database.specification;

import jakarta.persistence.criteria.*;
import org.waybill.account.management.database.entity.Account;
import org.waybill.account.management.database.entity.AccountContact;
import org.waybill.account.management.database.entity.Contact;
import org.waybill.account.management.dto.AccountContactFilter;
import org.springframework.data.jpa.domain.Specification;

public class AccountContactSpecification implements Specification<AccountContact> {

    private final AccountContactFilter criteria;

    public AccountContactSpecification(AccountContactFilter criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<AccountContact> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        Join<AccountContact, Account> accountJoin = root.join("account", JoinType.INNER);
        Join<AccountContact, Contact> contactJoin = root.join("contact", JoinType.INNER);

        var predicates = SPredicates.builder()
                .add(criteria.getAccountId(), accountId -> builder.equal(accountJoin.get("id"), accountId))
                .add(criteria.getApiKey(), apiKey -> builder.equal(accountJoin.get("apiKey"), apiKey))
                .add(criteria.getName(), name -> builder.like(builder.lower(accountJoin.get("name")), "%" + name.toLowerCase() + "%"))
                .add(criteria.getNickname(), nickname -> builder.like(builder.lower(accountJoin.get("nickname")), "%" + nickname.toLowerCase() + "%"))
                .add(criteria.getStatus(), status -> builder.equal(accountJoin.get("status"), status))
                .add(criteria.getFirstName(), firstName -> builder.like(builder.lower(contactJoin.get("firstName")), "%" + firstName.toLowerCase() + "%"))
                .add(criteria.getLastName(), lastName -> builder.like(builder.lower(contactJoin.get("lastName")), "%" + lastName.toLowerCase() + "%"))
                .add(criteria.getPhoneNumber(), phoneNumber -> builder.equal(contactJoin.get("phoneNumber"), phoneNumber)).build();

        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
