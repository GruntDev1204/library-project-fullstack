package com.library_management.api.service;

public interface InterfaceService<M, R , Td , P> {
   Td getAll(R requestParam , P options);

    M findById(Long id);

    M create(M data);

    Boolean delete(Long id);

    M update(Long id, M newData);

}
