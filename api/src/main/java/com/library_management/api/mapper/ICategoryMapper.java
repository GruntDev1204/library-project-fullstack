package com.library_management.api.mapper;

import com.library_management.api.dto.category.CategoryReq;
import com.library_management.api.dto.category.CategoryRes;
import com.library_management.api.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICategoryMapper {
    CategoryRes entityToRes(Category e);
    Category reqToEntity(CategoryReq req);
}
