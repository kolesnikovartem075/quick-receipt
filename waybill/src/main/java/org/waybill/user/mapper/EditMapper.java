package org.waybill.user.mapper;

public interface EditMapper<F, T> {

    T map(F fromObject, T toObject);

}
