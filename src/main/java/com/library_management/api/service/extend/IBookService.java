package com.library_management.api.service.extend;

import com.library_management.api.dto.book.BookReq;
import com.library_management.api.dto.book.BookRes;
import com.library_management.api.helper.filter_cols.FindBook;
import com.library_management.api.helper.pagination.PageOption;
import com.library_management.api.service.InterfaceService;
import org.springframework.data.domain.Page;

public interface IBookService extends InterfaceService<BookReq, BookRes> {
    Page<BookRes> getAll(FindBook filter, PageOption pageOption); ;
}
