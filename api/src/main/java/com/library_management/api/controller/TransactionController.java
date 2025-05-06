package com.library_management.api.controller;

import com.library_management.api.helper.filter_cols.FindTransaction;
import com.library_management.api.helper.pagination.PageOption;
import com.library_management.api.helper.code_status.ErrorCode;
import com.library_management.api.helper.code_status.SuccessCode;
import com.library_management.api.exception.ApiException;
import com.library_management.api.dto.transaction.TransactionReq;
import com.library_management.api.dto.transaction.TransactionRes;
import com.library_management.api.helper.response.ApiResponse;
import com.library_management.api.service.extend.ITransactionService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/transactions")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionController extends BaseController {
    ITransactionService sv;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<TransactionRes>>> getAll(FindTransaction requestParam, PageOption p) {
        return returnResponseJson(SuccessCode.GET_ALL_SUCCESS, sv.getAll(requestParam, p));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TransactionRes>> findById(@PathVariable Long id) {
        return returnResponseJson(SuccessCode.GET_BY_ID_SUCCESS,sv.findById(id));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TransactionRes>> create(@RequestBody TransactionReq data) throws ParseException, JOSEException {
        return returnResponseJson(SuccessCode.CREATE_SUCCESS, sv.create(data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        Boolean action = sv.delete(id);
        if (!action) throw new ApiException(ErrorCode.DELETED_FAILED);
        else return returnResponseJson(SuccessCode.DELETE_SUCCESS);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TransactionRes>> update(@PathVariable Long id, @RequestBody TransactionReq newReq) throws ParseException, JOSEException {
        return returnResponseJson(SuccessCode.UPDATE_SUCCESS, sv.update(id, newReq));
    }

    @GetMapping("/update-is-purchase/{id}")
    public ResponseEntity<ApiResponse<Void>> changeIsPurchase(@PathVariable Long id) {
        Boolean action = sv.changeIsPurchase(id);
        if (!action) throw new ApiException(ErrorCode.UPDATE_FAILED);
        return returnResponseJson(SuccessCode.UPDATE_SUCCESS);
    }
}
