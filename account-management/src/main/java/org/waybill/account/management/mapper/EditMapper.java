package org.waybill.account.management.mapper;

public interface EditMapper<F, T> {

    T map(F fromObject, T toObject);

}
