package org.waybill.order.database.specification;


import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SPredicates {

    private final List<Predicate> predicates = new ArrayList<>();

    public static SPredicates builder() {
        return new SPredicates();
    }

    public <T> SPredicates add(T object, Function<T, Predicate> function) {
        if (object != null) {
            predicates.add(function.apply(object));
        }
        return this;
    }

    public List<Predicate> build() {
       return predicates;
    }
}