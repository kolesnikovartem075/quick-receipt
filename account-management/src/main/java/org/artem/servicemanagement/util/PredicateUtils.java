package org.artem.servicemanagement.util;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

import java.util.Collection;

public final class PredicateUtils {


    public static <T> Predicate equal(CriteriaBuilder builder, Path<T> path, T value) {
        return builder.equal(path, value);
    }

    public static Predicate containsIgnoreCase(CriteriaBuilder builder, Path<String> path, String value) {
        return builder.like(builder.lower(path), "%" + value.toLowerCase() + "%");
    }

    public static Predicate startsWithIgnoreCase(CriteriaBuilder builder, Path<String> path, String value) {
        return builder.like(builder.lower(path), value.toLowerCase() + "%");
    }

    public static <T> Predicate in(Path<T> path, Collection<T> values) {
        return path.in(values);
    }

    public static <T extends Comparable<T>> Predicate greaterThanOrEqual(CriteriaBuilder builder, Path<T> path, T value) {
        return builder.greaterThanOrEqual(path, value);
    }

    public static <T extends Comparable<T>> Predicate lessThanOrEqual(CriteriaBuilder builder, Path<T> path, T value) {
        return builder.lessThanOrEqual(path, value);
    }
}