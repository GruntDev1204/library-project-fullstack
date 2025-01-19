package com.library_management.api.mapper.extend;

import com.library_management.api.dto.req_and_res.book.BookReq;
import com.library_management.api.dto.req_and_res.book.BookRes;
import com.library_management.api.mapper.IMapper;
import com.library_management.api.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface IBookMapper extends IMapper<Book , BookReq , BookRes> {
    @Mapping(source = "categoryId" , target = "category.id"  , nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    Book reqToEntity(BookReq req);

    @Mapping(source = "category.name" , target = "categoryName")
    @Mapping(source = "category.id" , target = "categoryId")
    BookRes entityToRes(Book e);
}
