package com.library_management.api.service.impl;

import com.library_management.api.dto.code_status.impl.ErrorCode;
import com.library_management.api.dto.exception.ApiException;
import com.library_management.api.model.Book;
import com.library_management.api.model.Transaction;
import com.library_management.api.repository.ITransactionRepository;
import com.library_management.api.service.InterfaceService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class TransactionService implements InterfaceService<Transaction, Object> {
    ITransactionRepository repository;

    @Override
    public List<Transaction> getAll(Object requestParam) {
        return repository.findAll();
    }

    @Override
    public Transaction findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ApiException(ErrorCode.GENERAL_NOT_FOUND));
    }

    @Override
    public Transaction create(Transaction data) {
        return repository.save(data);
    }

    @Override
    public Boolean delete(Long id) {
        this.findById(id);
        repository.deleteById(id);
        return true;
    }

    @Override
    public Transaction update(Long id, Transaction newData) {
        this.findById(id);
        newData.setId(id);
        return repository.save(newData);
    }
}
