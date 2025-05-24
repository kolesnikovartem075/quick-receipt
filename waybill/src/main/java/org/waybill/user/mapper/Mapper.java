package org.waybill.user.mapper;


public interface Mapper<F, T> extends CreateMapper<F, T>, EditMapper<F, T> {

    @Override
    default T map(F fromObject, T toObject) {
        return toObject;
    }
}
