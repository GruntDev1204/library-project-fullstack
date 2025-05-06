package com.library_management.api.controller;

import com.library_management.api.helper.code_status.SuccessCode;
import com.library_management.api.helper.response.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class BaseController {

    @SuppressWarnings("unchecked")
    private static <T> ApiResponse<T> isPageable(SuccessCode successCode, Page<T> page) {
        return ApiResponse.<T>builder()
                .code(successCode.getCode())
                .message(successCode.getMessage())
                .data((T) page.getContent())
                .pageDetail(Map.of(
                        "total_items", page.getTotalElements(),
                        "total_pages", page.getTotalPages(),
                        "size_of_a_page", page.getSize(),
                        "index_page", page.getNumber() + 1
                ))
                .build();
    }

    @SuppressWarnings("unchecked")
    protected static <T> ResponseEntity<ApiResponse<T>> returnResponseJson(SuccessCode successCode, T data) {
        ApiResponse<T> response;

        if (data instanceof Page<?> page) {
            response = (ApiResponse<T>) isPageable(successCode, page);
        } else {
            response = ApiResponse.<T>builder()
                    .code(successCode.getCode())
                    .message(successCode.getMessage())
                    .data((T) data)
                    .pageDetail(null)
                    .build();
        }
        return ResponseEntity.status(successCode.getStatus()).body(response);
    }

    protected static ResponseEntity<ApiResponse<Void>> returnResponseJson(SuccessCode successCode) {
        return returnResponseJson(successCode, null);
    }

    @RequestMapping("/api")
    public ResponseEntity<ApiResponse<Boolean>> checkApi() {
        return returnResponseJson(SuccessCode.API_IS_VAILD, true);
    }
}
