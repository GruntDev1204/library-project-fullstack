package com.library_management.api.controller.impl;

import com.library_management.api.attribute.attribute_class.FindTransaction;
import com.library_management.api.attribute.pagination.PageOption;
import com.library_management.api.controller.RestControllerInterface;
import com.library_management.api.dto.code_status.impl.ErrorCode;
import com.library_management.api.dto.code_status.impl.SuccessCode;
import com.library_management.api.dto.exception.ApiException;
import com.library_management.api.dto.req_and_res.transaction.TransactionReq;
import com.library_management.api.dto.req_and_res.transaction.TransactionRes;
import com.library_management.api.dto.response.ApiResponse;
import com.library_management.api.dto.response.HandleResponseData;
import com.library_management.api.mapper.extend.ITransactionMapper;
import com.library_management.api.service.impl.TransactionSV.IAdditionalTransaction;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionController implements RestControllerInterface<TransactionReq, TransactionRes, FindTransaction, Page<TransactionRes>, PageOption> {
    IAdditionalTransaction sv;
    ITransactionMapper mapper;
    HandleResponseData res;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<TransactionRes>>> getAll(FindTransaction requestParam, PageOption p) {
        return res.returnResponseJson(SuccessCode.GET_ALL_SUCCESS, sv.getAll(requestParam, p).map(mapper::entityToRes));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TransactionRes>> findById(@PathVariable Long id) {
        return res.returnResponseJson(SuccessCode.GET_BY_ID_SUCCESS, mapper.entityToRes(sv.findById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TransactionRes>> create(@RequestBody TransactionReq data) {
        return res.returnResponseJson(SuccessCode.CREATE_SUCCESS, mapper.entityToRes(sv.create(mapper.reqToEntity(data))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        Boolean action = sv.delete(id);
        if (!action)
            throw new ApiException(ErrorCode.DELETED_FAILED);
        else
            return res.returnResponseJson(SuccessCode.DELETE_SUCCESS);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TransactionRes>> update(@PathVariable Long id, @RequestBody TransactionReq newData) {
        return res.returnResponseJson(SuccessCode.UPDATE_SUCCESS, mapper.entityToRes(sv.update(id, mapper.reqToEntity(newData))));
    }

    @GetMapping("/update-is-purchase/{id}")
    public ResponseEntity<ApiResponse<Void>> changeIsPurchase(@PathVariable Long id) {
        Boolean action = sv.changeIsPurchase(id);
        if (!action) throw new ApiException(ErrorCode.UPDATE_FAILED);
        return res.returnResponseJson(SuccessCode.UPDATE_SUCCESS);
    }
}
