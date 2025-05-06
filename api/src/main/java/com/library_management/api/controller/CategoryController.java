package com.library_management.api.controller;

import com.library_management.api.helper.code_status.ErrorCode;
import com.library_management.api.helper.code_status.SuccessCode;
import com.library_management.api.exception.ApiException;
import com.library_management.api.dto.category.CategoryReq;
import com.library_management.api.dto.category.CategoryRes;
import com.library_management.api.helper.response.ApiResponse;
import com.library_management.api.service.extend.ICategoryService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryController extends BaseController  {
    ICategoryService sv;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryRes>>> getAll(Object requestParam, Object options) {
        return returnResponseJson(SuccessCode.GET_ALL_SUCCESS, sv.getAll(requestParam, options));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryRes>> findById(@PathVariable Long id) {
        return returnResponseJson(SuccessCode.GET_BY_ID_SUCCESS, sv.findById(id));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryRes>> create(@Validated  @RequestBody CategoryReq data) throws ParseException, JOSEException {
        return returnResponseJson(SuccessCode.CREATE_SUCCESS,sv.create(data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        Boolean action = sv.delete(id);
        if (!action) throw new ApiException(ErrorCode.DELETED_FAILED);
        return returnResponseJson(SuccessCode.DELETE_SUCCESS);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryRes>> update(@PathVariable Long id, @Validated @RequestBody CategoryReq newReq) throws ParseException, JOSEException {
        return returnResponseJson(SuccessCode.UPDATE_SUCCESS,sv.update(id, newReq));
    }
}
