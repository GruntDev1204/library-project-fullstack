package com.library_management.api.service.impl;

import com.library_management.api.dto.book.BookReq;
import com.library_management.api.dto.book.BookRes;
import com.library_management.api.helper.filter_cols.FindBook;
import com.library_management.api.helper.filter_data.BookFilter;
import com.library_management.api.helper.pagination.PageOption;
import com.library_management.api.helper.code_status.ErrorCode;
import com.library_management.api.exception.ApiException;
import com.library_management.api.mapper.IBookMapper;
import com.library_management.api.model.Book;
import com.library_management.api.repository.IBookRepository;
import com.library_management.api.service.BaseService;
import com.library_management.api.service.extend.IBookService;
import lombok.*;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Primary
public class BookService extends BaseService implements IBookService {
    IBookRepository rp;
    IBookMapper mapper;

    private Book returnById(Long id){
        return rp.findById(id).orElseThrow(() -> new ApiException(ErrorCode.GENERAL_NOT_FOUND));
    }

    @Override
    public Page<BookRes> getAll(FindBook filterBook, PageOption options) {
        return rp.findAll(BookFilter.getFilter(filterBook), dataPageOption(options)).map(mapper::entityToRes);
    }

    @Override
    public BookRes findById(Long id) {
    return mapper.entityToRes(returnById(id));
}

    @Override
    public BookRes create(BookReq req) {
        Book data = mapper.reqToEntity(req);
        String dfAvatar = (data.getAvatar() == null || data.getAvatar().isEmpty()) ?
                "https://firebasestorage.googleapis.com/v0/b/trung1204-bdc27.appspot.com/o/lib_management%2FoldBook.png?alt=media&token=868f0b6d-5e7d-4df8-a816-3fefd049d511"
                : data.getAvatar();
        data.setAvatar(dfAvatar);

        if (data.getCategory().getId() == null) {
            data.setCategory(null);
        }

        return mapper.entityToRes(rp.save(data));
    }

    @Override
    public Boolean delete(Long id) {
        this.findById(id);
        rp.deleteById(id);
        return true;
    }

    @Override
    public BookRes update(Long id, BookReq newReq) {
        Book newData = mapper.reqToEntity(newReq);
        newData.setTransactionVolume(returnById(id).getTransactionVolume());
        newData.setId(id);
        return mapper.entityToRes(rp.save(newData));
    }
}
