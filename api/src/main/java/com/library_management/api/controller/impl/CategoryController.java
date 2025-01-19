package com.library_management.api.controller.impl;

import com.library_management.api.controller.RestControllerInterface;
import com.library_management.api.dto.code_status.impl.ErrorCode;
import com.library_management.api.dto.code_status.impl.SuccessCode;
import com.library_management.api.dto.exception.ApiException;
import com.library_management.api.dto.req_and_res.category.CategoryReq;
import com.library_management.api.dto.req_and_res.category.CategoryRes;
import com.library_management.api.mapper.extend.ICategoryMapper;
import com.library_management.api.dto.response.ApiResponse;
import com.library_management.api.dto.response.HandleResponseData;
import com.library_management.api.model.Category;
import com.library_management.api.service.InterfaceService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryController implements RestControllerInterface<CategoryReq, CategoryRes, Object, List<CategoryRes>, Object> {
    InterfaceService<Category, Object, List<Category>, Object> sv;
    HandleResponseData res;
    ICategoryMapper mapper;

    @Override
    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryRes>>> getAll(Object requestParam, Object options) {
        return res.returnResponseJson(SuccessCode.GET_ALL_SUCCESS, sv.getAll(requestParam, options).stream().map(mapper::entityToRes).collect(Collectors.toList()));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryRes>> findById(@PathVariable Long id) {
        return res.returnResponseJson(SuccessCode.GET_BY_ID_SUCCESS, mapper.entityToRes(sv.findById(id)));
    }

    @Override
    @PostMapping
    public ResponseEntity<ApiResponse<CategoryRes>> create(@RequestBody CategoryReq data) {
        return res.returnResponseJson(SuccessCode.CREATE_SUCCESS, mapper.entityToRes(sv.create(mapper.reqToEntity(data))));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        Boolean action = sv.delete(id);
        if (!action) throw new ApiException(ErrorCode.DELETED_FAILED);
        return res.returnResponseJson(SuccessCode.DELETE_SUCCESS);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryRes>> update(@PathVariable Long id, @RequestBody CategoryReq newData) {
        return res.returnResponseJson(SuccessCode.UPDATE_SUCCESS, mapper.entityToRes(sv.update(id, mapper.reqToEntity(newData))));
    }
}
