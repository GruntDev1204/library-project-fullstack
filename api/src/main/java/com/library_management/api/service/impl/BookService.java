package com.library_management.api.service.impl;

import com.library_management.api.dto.code_status.impl.ErrorCode;
import com.library_management.api.dto.exception.ApiException;
import com.library_management.api.model.Book;
import com.library_management.api.repository.IBookRepository;
import com.library_management.api.service.InterfaceService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class BookService implements InterfaceService<Book, Object> {
    IBookRepository rp;

    @Override
    public List<Book> getAll(Object requestParam) {
        return rp.findAll();
    }

    @Override
    public Book findById(Long id) {
        return rp.findById(id).orElseThrow(() -> new ApiException(ErrorCode.GENERAL_NOT_FOUND));
    }

    @Override
    public Book create(Book data) {
        return rp.save(data);
    }

    @Override
    public Boolean delete(Long id) {
        this.findById(id);
        rp.deleteById(id);
        return true;
    }

    @Override
    public Book update(Long id, Book newData) {
        this.findById(id);
        newData.setId(id);
        return rp.save(newData);
    }
}
