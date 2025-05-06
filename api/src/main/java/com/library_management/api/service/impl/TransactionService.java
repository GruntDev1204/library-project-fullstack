package com.library_management.api.service.impl;

import com.library_management.api.dto.book.BookReq;
import com.library_management.api.dto.book.BookRes;
import com.library_management.api.dto.transaction.TransactionReq;
import com.library_management.api.dto.transaction.TransactionRes;
import com.library_management.api.helper.filter_cols.FindTransaction;
import com.library_management.api.helper.filter_data.TransactionFilter;
import com.library_management.api.helper.pagination.PageOption;
import com.library_management.api.helper.code_status.ErrorCode;
import com.library_management.api.exception.ApiException;
import com.library_management.api.dto.authentication.AuthReq;
import com.library_management.api.dto.authentication.AuthRes;
import com.library_management.api.dto.customer.CustomerReq;
import com.library_management.api.dto.customer.CustomerRes;
import com.library_management.api.mapper.IBookMapper;
import com.library_management.api.mapper.ITransactionMapper;
import com.library_management.api.model.Book;
import com.library_management.api.model.Customer;
import com.library_management.api.model.Transaction;
import com.library_management.api.repository.ICustomerRepository;
import com.library_management.api.repository.ITransactionRepository;
import com.library_management.api.service.BaseService;
import com.library_management.api.service.extend.IBookService;
import com.library_management.api.service.extend.ITransactionService;
import com.library_management.api.service.InterfaceAuthService;
import com.nimbusds.jose.JOSEException;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@AllArgsConstructor
@Primary
public class TransactionService extends BaseService implements ITransactionService {
    ITransactionRepository repository;
    ITransactionMapper mapper;
    ICustomerRepository customerRepository;
    IBookMapper bookMapper;
    IBookService bS;
    InterfaceAuthService<AuthReq, AuthRes, CustomerReq, CustomerRes> auth;

    private Transaction returnById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ApiException(ErrorCode.GENERAL_NOT_FOUND));
    }

    private void autoSetCol(Transaction tr) {
        if (!tr.getIsPurchased()) {
            tr.setIsPurchased(true);
            tr.setStatus("RETURNED");
            tr.setExpiredDate(tr.getTransactionDate());
        } else {
            tr.setIsPurchased(false);
            tr.setExpiredDate(LocalDateTime.now().plusHours(72));
            tr.setStatus("BORROWED");
        }
    }

    private void autoSetColCreate(Transaction tr) throws ParseException, JOSEException {
        Book b = bookMapper.resToEntity(bS.findById(tr.getBook().getId()));
        if (tr.getIsPurchased()) {
            tr.setStatus("RETURNED");
            tr.setExpiredDate(tr.getTransactionDate());
            tr.setSinglePrice(b.getPrice());
        } else {
            tr.setStatus("PENDING");
            tr.setExpiredDate(LocalDateTime.now().plusHours(72));
            tr.setSinglePrice(b.getPrice()*0.5);
        }
        b.setTransactionVolume(b.getTransactionVolume() + 1);
        bS.update(b.getId(), bookMapper.entityToReq(b));
    }

    private void autoSetColUpdate(@NotNull Transaction tr) {
        Transaction t = this.returnById(tr.getId());
        Book b = bookMapper.resToEntity(bS.findById(tr.getBook().getId()));
        tr.setTransactionDate(t.getTransactionDate());
        if (tr.getIsPurchased()) {
            tr.setExpiredDate(tr.getTransactionDate());
            tr.setSinglePrice(b.getPrice());
        } else {
            tr.setSinglePrice(b.getPrice()*0.5);
            if (Duration.between(t.getExpiredDate(), LocalDateTime.now()).toHours() < 24) {
                tr.setExpiredDate(t.getExpiredDate().plusHours(24));
            }else{
                tr.setExpiredDate(t.getExpiredDate());
            }
        }
    }

    private void checkUpdateQuantity(Transaction newData, Transaction oldData) throws ParseException, JOSEException {
        Book b = bookMapper.resToEntity(bS.findById(newData.getBook().getId()));
        long bookDifference = (oldData == null)
                ? newData.getNumberOfBooks()
                : newData.getNumberOfBooks() - oldData.getNumberOfBooks();

        if (bookDifference > 0 && b.getQuantity() < bookDifference) {
            throw new ApiException(ErrorCode.CREATE_FAILED, Map.of("Book", "Book in stock not enough"));
        }

        b.setQuantity(b.getQuantity() - bookDifference);
        bS.update(b.getId(), bookMapper.entityToReq(b));
    }

    private void convertEmail(CustomerRes auth , Transaction data) {
        Customer c  = customerRepository.findByEmail(auth.getEmail());
        data.setCustomer(c);
    }

    @Override
    public Page<TransactionRes> getAll(FindTransaction filter, PageOption pageOption) {
        return repository.findAll(TransactionFilter.getFilter(filter), dataPageOption(pageOption)).map(mapper::entityToRes);
    }

    @Override
    public TransactionRes findById(Long id) {
        return mapper.entityToRes(this.returnById(id));
    }

    @Override
    public TransactionRes create(TransactionReq req) throws ParseException, JOSEException {
        Transaction transaction = mapper.reqToEntity(req);
        this.checkUpdateQuantity(transaction, null);
        transaction.setTransactionDate(LocalDateTime.now());
        this.autoSetColCreate(transaction);
        return mapper.entityToRes(repository.save(transaction));
    }

    @Override
    public Boolean delete(Long id) {
        this.findById(id);
        repository.deleteById(id);
        return true;
    }

    @Override
    public TransactionRes update(Long id, TransactionReq newReq) throws ParseException, JOSEException {
        Transaction newData = mapper.reqToEntity(newReq);
        Transaction tr = this.returnById(id);
        this.checkUpdateQuantity(newData, tr);
        newData.setId(id);
        this.autoSetColUpdate(newData);
        return mapper.entityToRes(repository.save(newData));
    }

    @Override
    public Boolean changeIsPurchase(Long id) {
        Transaction tr = this.returnById(id);
        this.autoSetCol(tr);
        repository.save(tr);
        return true;
    }


}
