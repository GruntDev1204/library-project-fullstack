package com.library_management.api.service.impl;

import com.library_management.api.dto.code_status.impl.ErrorCode;
import com.library_management.api.dto.exception.ApiException;
import com.library_management.api.model.User;
import com.library_management.api.repository.IUserRepository;
import com.library_management.api.service.InterfaceService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class UserService implements InterfaceService<User, Object> {
    IUserRepository repository;

    @Override
    public List<User> getAll(Object requestParam) {
        return repository.findAll();
    }

    @Override
    public User findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ApiException(ErrorCode.GENERAL_NOT_FOUND));
    }

    @Override
    public User create(User data) {
        return repository.save(data);
    }

    @Override
    public Boolean delete(Long id) {
        this.findById(id);
        repository.deleteById(id);
        return true;
    }

    @Override
    public User update(Long id, User newData) {
        this.findById(id);
        newData.setId(id);
        return repository.save(newData);
    }
}
