package org.artem.servicemanagement.dto;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.artem.servicemanagement.database.entity.Contact;
import org.springframework.data.jpa.domain.Specification;

public class ContactSpecification implements Specification<Contact> {

    private final ContactFilter criteria;

    public ContactSpecification(ContactFilter criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Contact> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return null;
    }
}
