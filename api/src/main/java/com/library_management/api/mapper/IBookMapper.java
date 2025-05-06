package com.library_management.api.mapper;

import com.library_management.api.dto.book.BookReq;
import com.library_management.api.dto.book.BookRes;
import com.library_management.api.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface IBookMapper {
    @Mapping(source = "categoryId", target = "category.id", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    Book reqToEntity(BookReq req);

    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "category.id", target = "categoryId")
    BookRes entityToRes(Book e);

    @Mapping(source = "category.id", target = "categoryId", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    BookReq entityToReq(Book e);

    @Mapping(source = "categoryName", target = "category.name")
    @Mapping(source = "categoryId", target = "category.id")
    Book resToEntity(BookRes e);
}
