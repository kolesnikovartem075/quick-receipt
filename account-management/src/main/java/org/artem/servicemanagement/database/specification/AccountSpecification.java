package org.artem.servicemanagement.database.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.artem.servicemanagement.database.entity.Account;
import org.artem.servicemanagement.dto.AccountFilter;
import org.springframework.data.jpa.domain.Specification;

public class AccountSpecification implements Specification<Account> {


    private final AccountFilter criteria;

    public AccountSpecification(AccountFilter criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Account> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        var predicates = SPredicates.builder()
//                .add(criteria.accountId(), id -> builder.equal(root.get("accountId"), id))
                .build();

        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
