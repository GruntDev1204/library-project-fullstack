package com.library_management.api.service.impl;

import com.library_management.api.dto.code_status.impl.ErrorCode;
import com.library_management.api.dto.exception.ApiException;
import com.library_management.api.model.Category;
import com.library_management.api.repository.ICategoryRepository;
import com.library_management.api.service.InterfaceService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Primary
public class CategoryService implements InterfaceService<Category, Object, List<Category>, Object> {
    ICategoryRepository rp;

    @Override
    public List<Category> getAll(Object requestParam, Object o) {
        return rp.findAll();
    }

    @Override
    public Category findById(Long id) {
        return rp.findById(id).orElseThrow(() -> new ApiException(ErrorCode.GENERAL_NOT_FOUND));
    }

    @Override
    public Category create(Category data) {
        return rp.save(data);
    }

    @Override
    public Boolean delete(Long id) {
        this.findById(id);
        rp.deleteById(id);
        return true;
    }

    @Override
    public Category update(Long id, Category newData) {
        this.findById(id);
        newData.setId(id);
        rp.save(newData);
        return newData;
    }
}
