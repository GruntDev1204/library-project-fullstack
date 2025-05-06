package com.library_management.api.mapper;

import com.library_management.api.dto.transaction.TransactionReq;
import com.library_management.api.dto.transaction.TransactionRes;
import com.library_management.api.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ITransactionMapper {

    @Mapping(source = "bookId", target = "book.id")
    Transaction reqToEntity(TransactionReq req);

    @Mapping(source = "customer.email", target = "email")
    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "book.name", target = "bookName")
    TransactionRes entityToRes(Transaction e);
}
