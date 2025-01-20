package com.library_management.api.mapper.extend;

import com.library_management.api.dto.req_and_res.category.CategoryReq;
import com.library_management.api.dto.req_and_res.category.CategoryRes;
import com.library_management.api.mapper.IMapper;
import com.library_management.api.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICategoryMapper extends IMapper<Category, CategoryReq, CategoryRes> {
}
