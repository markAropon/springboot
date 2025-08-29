
package com.bootcamp.quickdemo.mappers;

public interface MapperConfig<E, D> {
    D toDto(E entity);
    E toEntity(D dto);
}