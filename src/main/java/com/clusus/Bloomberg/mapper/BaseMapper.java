package com.clusus.Bloomberg.mapper;

import java.util.List;

public interface BaseMapper<E, D> {

    E mapToEntity(D d);

    D mapToDTO(E e);

    List<D> mapToDTOs(List<E> e);

    List<E> mapToEntities(List<D> d);

}
