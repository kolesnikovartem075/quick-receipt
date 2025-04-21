package org.waybill.account.management.mapper;

public interface CreateMapper<F, T> {

    T map(F object);
}