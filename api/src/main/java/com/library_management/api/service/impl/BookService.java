package com.library_management.api.service.impl;

import com.library_management.api.attribute.attribute_class.FindBook;
import com.library_management.api.attribute.attribute_data.InterfaceAttributeData;
import com.library_management.api.attribute.pagination.HandlePageOptionData;
import com.library_management.api.attribute.pagination.PageOption;
import com.library_management.api.dto.code_status.impl.ErrorCode;
import com.library_management.api.dto.exception.ApiException;
import com.library_management.api.model.Book;
import com.library_management.api.repository.IBookRepository;
import com.library_management.api.service.InterfaceService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Primary
public class BookService implements InterfaceService<Book, FindBook, Page<Book>, PageOption> {
    IBookRepository rp;
    HandlePageOptionData pageOption;
    InterfaceAttributeData<FindBook, Book> attributeData;

    @Override
    public Page<Book> getAll(FindBook requestParam, PageOption options) {
        return rp.findAll(attributeData.getAttribute(requestParam), pageOption.dataPageOption(options));
    }

    @Override
    public Book findById(Long id) {
        return rp.findById(id).orElseThrow(() -> new ApiException(ErrorCode.GENERAL_NOT_FOUND));
    }

    @Override
    public Book create(Book data) {
        String dfAvatar = (data.getAvatar() == null || data.getAvatar().isEmpty()) ?
                "https://firebasestorage.googleapis.com/v0/b/trung1204-bdc27.appspot.com/o/lib_management%2FoldBook.png?alt=media&token=868f0b6d-5e7d-4df8-a816-3fefd049d511"
                : data.getAvatar();
        data.setAvatar(dfAvatar);
        if (data.getCategory().getId() == null) {
            data.setCategory(null);
        }
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
        Book b = this.findById(id);
        newData.setTransactionVolume(b.getTransactionVolume());
        newData.setId(id);
        return rp.save(newData);
    }
}
