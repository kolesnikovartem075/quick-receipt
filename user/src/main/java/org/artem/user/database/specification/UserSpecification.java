package org.artem.user.database.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.artem.user.database.entity.User;
import org.artem.user.dto.UserFilter;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification implements Specification<User> {


    private final UserFilter criteria;

    public UserSpecification(UserFilter criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        var predicates = SPredicates.builder()
                .add(criteria.telegramId(), telegramId -> builder.equal(root.get("telegramId"), telegramId))
                .add(criteria.firstName(), firstName -> builder.like(root.get("firstName"), firstName))
                .add(criteria.lastName(), lastName -> builder.like(root.get("lastName"), lastName))
                .add(criteria.phoneNumber(), phoneNumber -> builder.equal(root.get("phoneNumber"), phoneNumber))
                .build();

        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
