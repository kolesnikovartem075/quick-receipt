package org.artem.servicemanagement.mapper;

public interface CreateMapper<F, T> {

    T map(F object);
}