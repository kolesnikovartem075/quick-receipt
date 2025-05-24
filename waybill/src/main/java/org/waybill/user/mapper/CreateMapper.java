package org.waybill.user.mapper;

public interface CreateMapper<F, T> {

    T map(F object);
}