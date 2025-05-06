package com.library_management.api.controller;

import com.library_management.api.helper.filter_cols.FindBook;
import com.library_management.api.helper.pagination.PageOption;
import com.library_management.api.helper.code_status.ErrorCode;
import com.library_management.api.helper.code_status.SuccessCode;
import com.library_management.api.exception.ApiException;
import com.library_management.api.dto.book.BookReq;
import com.library_management.api.dto.book.BookRes;
import com.library_management.api.helper.response.ApiResponse;
import com.library_management.api.service.extend.IBookService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/books")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookController extends BaseController {
    IBookService sv;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<BookRes>>> getAll(FindBook requestParam, PageOption p) {
        return returnResponseJson(SuccessCode.GET_ALL_SUCCESS, sv.getAll(requestParam, p));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookRes>> findById(@PathVariable Long id) {
        return returnResponseJson(SuccessCode.GET_BY_ID_SUCCESS,sv.findById(id));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BookRes>> create(@Validated @RequestBody BookReq data) throws ParseException, JOSEException {
        return returnResponseJson(SuccessCode.CREATE_SUCCESS, sv.create(data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        Boolean action = sv.delete(id);
        if (action) {
            return returnResponseJson(SuccessCode.DELETE_SUCCESS);
        } else {
            throw new ApiException(ErrorCode.DELETED_FAILED);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BookRes>> update(@PathVariable Long id, @Validated @RequestBody BookReq newData) throws ParseException, JOSEException {
        return returnResponseJson(SuccessCode.UPDATE_SUCCESS,sv.update(id,newData));
    }
}
