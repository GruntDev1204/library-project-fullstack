package com.library_management.api.controller.impl;

import com.library_management.api.controller.RestControllerInterface;
import com.library_management.api.dto.code_status.impl.ErrorCode;
import com.library_management.api.dto.code_status.impl.SuccessCode;
import com.library_management.api.dto.exception.ApiException;
import com.library_management.api.dto.response.ApiResponse;
import com.library_management.api.dto.response.HandleResponseData;
import com.library_management.api.model.Book;
import com.library_management.api.service.InterfaceService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookController implements RestControllerInterface<Book, Object> {
    HandleResponseData res;
    InterfaceService<Book, Object> sv;

    @Override
    @GetMapping
    public ResponseEntity<ApiResponse<List<Book>>> getAll(Object requestParam) {
        return res.returnResponseJson(SuccessCode.GET_ALL_SUCCESS, sv.getAll(requestParam));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Book>> findById(@PathVariable Long id) {
        return res.returnResponseJson(SuccessCode.GET_BY_ID_SUCCESS, sv.findById(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<ApiResponse<Book>> create(@RequestBody Book data) {
        return res.returnResponseJson(SuccessCode.CREATE_SUCCESS, sv.create(data));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        Boolean action = sv.delete(id);
        if (action) {
            return res.returnResponseJson(SuccessCode.DELETE_SUCCESS);
        } else {
            throw new ApiException(ErrorCode.DELETED_FAILED);
        }
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Book>> update(@PathVariable Long id, @RequestBody Book newData) {
        return res.returnResponseJson(SuccessCode.UPDATE_SUCCESS, sv.update(id, newData));
    }
}
