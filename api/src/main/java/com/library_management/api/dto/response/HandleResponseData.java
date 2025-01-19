package com.library_management.api.dto.response;

import com.library_management.api.dto.code_status.impl.SuccessCode;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class HandleResponseData {
    @SuppressWarnings("unchecked")
    private <T> ApiResponse<T> isPageable(SuccessCode successCode, Page<T> page) {
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
    public <T> ResponseEntity<ApiResponse<T>> returnResponseJson(SuccessCode successCode, T data) {
        ApiResponse<T> response;

        if (data instanceof Page<?> page) {
            response = (ApiResponse<T>) this.isPageable(successCode, page);
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

    public ResponseEntity<ApiResponse<Void>> returnResponseJson(SuccessCode successCode) {
        return this.returnResponseJson(successCode, null);
    }
}