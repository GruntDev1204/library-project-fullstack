package com.library_management.api.service.impl;

import com.library_management.api.dto.category.CategoryReq;
import com.library_management.api.dto.category.CategoryRes;
import com.library_management.api.helper.code_status.ErrorCode;
import com.library_management.api.exception.ApiException;
import com.library_management.api.mapper.ICategoryMapper;
import com.library_management.api.model.Category;
import com.library_management.api.repository.ICategoryRepository;
import com.library_management.api.service.BaseService;
import com.library_management.api.service.extend.ICategoryService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Primary
public class CategoryService extends BaseService implements ICategoryService {
    ICategoryRepository rp;
    ICategoryMapper mapper;

    @Override
    public List<CategoryRes> getAll(Object filter, Object options) {
        return rp.findAll().stream().map(mapper::entityToRes).collect(Collectors.toList());
    }

    @Override
    public CategoryRes findById(Long id) {
        return mapper.entityToRes(rp.findById(id).orElseThrow(() -> new ApiException(ErrorCode.GENERAL_NOT_FOUND)));
    }

    @Override
    public CategoryRes create(CategoryReq data) {
        return mapper.entityToRes(rp.save(mapper.reqToEntity(data)));
    }

    @Override
    public Boolean delete(Long id) {
        this.findById(id);
        rp.deleteById(id);
        return true;
    }

    @Override
    public CategoryRes update(Long id, CategoryReq newReq) {
        Category newData = mapper.reqToEntity(newReq);
        this.findById(id);
        newData.setId(id);
        return mapper.entityToRes(rp.save(newData));
    }
}
