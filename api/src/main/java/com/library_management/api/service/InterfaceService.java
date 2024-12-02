package com.library_management.api.service;

import java.util.List;

public interface InterfaceService<M, R> {
    List<M> getAll(R requestParam);

    M findById(Long id);

    M create(M data);

    Boolean delete(Long id);

    M update(Long id, M newData);
}
