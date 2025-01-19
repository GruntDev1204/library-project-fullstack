package com.library_management.api.mapper.extend;

import com.library_management.api.dto.req_and_res.transaction.TransactionReq;
import com.library_management.api.dto.req_and_res.transaction.TransactionRes;
import com.library_management.api.mapper.IMapper;
import com.library_management.api.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ITransactionMapper extends IMapper<Transaction, TransactionReq, TransactionRes> {

    @Mapping(source = "email", target = "customer.email")
    @Mapping(source = "bookId", target = "book.id")
    Transaction reqToEntity(TransactionReq req);

    @Mapping(source = "customer.email", target = "email")
    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "book.name", target = "bookName")
    TransactionRes entityToRes(Transaction e);
}
