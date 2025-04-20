package org.artem.servicemanagement.database.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.artem.servicemanagement.database.entity.UserContactProfile;
import org.artem.servicemanagement.dto.UserContactProfileFilter;
import org.springframework.data.jpa.domain.Specification;

public class UserContactProfileSpecification implements Specification<UserContactProfile> {

    private final UserContactProfileFilter criteria;

    public UserContactProfileSpecification(UserContactProfileFilter criteria) {
        this.criteria = criteria;
    }


    @Override
    public Predicate toPredicate(Root<UserContactProfile> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return null;
    }
}
