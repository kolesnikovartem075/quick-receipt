package org.waybill.account.management.database.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.waybill.account.management.database.entity.Account;
import org.waybill.account.management.dto.AccountFilter;
import org.springframework.data.jpa.domain.Specification;

public class AccountSpecification implements Specification<Account> {


    private final AccountFilter criteria;

    public AccountSpecification(AccountFilter criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Account> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        var predicates = SPredicates.builder()
                .add(criteria.name(), name -> builder.equal(root.get("name"), name))
                .add(criteria.nickname(), nickname -> builder.equal(root.get("nickname"), nickname))
                .add(criteria.status(), status -> builder.equal(root.get("status"), status))
                .build();

        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
