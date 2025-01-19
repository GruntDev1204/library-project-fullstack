package com.library_management.api.controller.impl;

import com.library_management.api.attribute.attribute_class.FindBook;
import com.library_management.api.attribute.pagination.PageOption;
import com.library_management.api.controller.RestControllerInterface;
import com.library_management.api.dto.code_status.impl.ErrorCode;
import com.library_management.api.dto.code_status.impl.SuccessCode;
import com.library_management.api.dto.exception.ApiException;
import com.library_management.api.dto.req_and_res.book.BookReq;
import com.library_management.api.dto.req_and_res.book.BookRes;
import com.library_management.api.mapper.extend.IBookMapper;
import com.library_management.api.dto.response.ApiResponse;
import com.library_management.api.dto.response.HandleResponseData;
import com.library_management.api.model.Book;
import com.library_management.api.service.InterfaceService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookController implements RestControllerInterface<BookReq, BookRes ,  FindBook , Page<BookRes> , PageOption> {
    HandleResponseData res;
    InterfaceService<Book, FindBook , Page<Book> , PageOption> sv;
    IBookMapper mapper;

    @Override
    @GetMapping
    public ResponseEntity<ApiResponse<Page<BookRes>>> getAll(FindBook requestParam , PageOption p) {
        return res.returnResponseJson(SuccessCode.GET_ALL_SUCCESS, sv.getAll(requestParam , p).map(mapper::entityToRes));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookRes>> findById(@PathVariable Long id) {
        return res.returnResponseJson(SuccessCode.GET_BY_ID_SUCCESS, mapper.entityToRes(sv.findById(id)));
    }

    @Override
    @PostMapping
    public ResponseEntity<ApiResponse<BookRes>> create(@Validated @RequestBody BookReq data) {
        return res.returnResponseJson(SuccessCode.CREATE_SUCCESS, mapper.entityToRes(sv.create(mapper.reqToEntity(data))));
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
    public ResponseEntity<ApiResponse<BookRes>> update(@PathVariable Long id, @Validated @RequestBody  BookReq newData) {
        return res.returnResponseJson(SuccessCode.UPDATE_SUCCESS, mapper.entityToRes(sv.update(id, mapper.reqToEntity(newData))));
    }
}
