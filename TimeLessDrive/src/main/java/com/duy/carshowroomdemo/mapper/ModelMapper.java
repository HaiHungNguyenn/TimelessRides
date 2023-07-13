package com.duy.carshowroomdemo.mapper;

public class ModelMapper<E, D> implements Mapper<E, D> {
    Class<E> entityType;
    Class<D> dtoType;

    @Override
    public E toEntity(D source) {
        return (source == null) ? null : modelMapper.map(source, entityType);
    }

    @Override
    public D toDto(E source) {
        return (source == null) ? null : modelMapper.map(source, dtoType);
    }
}
