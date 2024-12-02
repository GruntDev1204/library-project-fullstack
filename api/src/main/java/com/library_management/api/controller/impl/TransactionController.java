package com.library_management.api.controller.impl;

import com.library_management.api.controller.RestControllerInterface;
import com.library_management.api.dto.code_status.impl.ErrorCode;
import com.library_management.api.dto.code_status.impl.SuccessCode;
import com.library_management.api.dto.exception.ApiException;
import com.library_management.api.dto.response.ApiResponse;
import com.library_management.api.dto.response.HandleResponseData;
import com.library_management.api.model.Transaction;
import com.library_management.api.service.InterfaceService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionController implements RestControllerInterface<Transaction, Object> {
    InterfaceService<Transaction, Object> sv;
    HandleResponseData res;

    @Override
    @GetMapping
    public ResponseEntity<ApiResponse<List<Transaction>>> getAll(Object requestParam) {
        return res.returnResponseJson(SuccessCode.GET_ALL_SUCCESS, sv.getAll(requestParam));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Transaction>> findById(@PathVariable Long id) {
        return res.returnResponseJson(SuccessCode.GET_BY_ID_SUCCESS, sv.findById(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<ApiResponse<Transaction>> create(@RequestBody Transaction data) {
        return res.returnResponseJson(SuccessCode.CREATE_SUCCESS, sv.create(data));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        Boolean action = sv.delete(id);
        if (!action)
            throw new ApiException(ErrorCode.DELETED_FAILED);
        else
            return res.returnResponseJson(SuccessCode.DELETE_SUCCESS);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Transaction>> update(@PathVariable Long id, @RequestBody Transaction newData) {
        return res.returnResponseJson(SuccessCode.UPDATE_SUCCESS, sv.update(id, newData));
    }
}
