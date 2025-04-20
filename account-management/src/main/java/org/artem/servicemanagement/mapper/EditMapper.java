package org.artem.servicemanagement.mapper;

public interface EditMapper<F, T> {

    T map(F fromObject, T toObject);

}
