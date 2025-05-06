package com.library_management.api.service.extend;

import com.library_management.api.dto.category.CategoryReq;
import com.library_management.api.dto.category.CategoryRes;
import com.library_management.api.service.InterfaceService;
import java.util.List;

public interface ICategoryService extends InterfaceService<CategoryReq , CategoryRes> {
    List<CategoryRes> getAll(Object filter , Object pageOption);
}
