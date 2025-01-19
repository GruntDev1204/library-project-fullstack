package com.library_management.api.service.impl.TransactionSV;

import com.library_management.api.attribute.attribute_class.FindBook;
import com.library_management.api.attribute.attribute_class.FindTransaction;
import com.library_management.api.attribute.attribute_data.InterfaceAttributeData;
import com.library_management.api.attribute.pagination.HandlePageOptionData;
import com.library_management.api.attribute.pagination.PageOption;
import com.library_management.api.dto.code_status.impl.ErrorCode;
import com.library_management.api.dto.exception.ApiException;
import com.library_management.api.dto.req_and_res.transaction.TransactionReq;
import com.library_management.api.model.Book;
import com.library_management.api.model.Customer;
import com.library_management.api.model.Transaction;
import com.library_management.api.repository.ICustomerRepository;
import com.library_management.api.repository.ITransactionRepository;
import com.library_management.api.service.InterfaceService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@AllArgsConstructor
@Primary
public class TransactionService implements IAdditionalTransaction {
    ITransactionRepository repository;
    HandlePageOptionData hp;
    InterfaceService<Book, FindBook, Page<Book>, PageOption> bS;
    InterfaceAttributeData<FindTransaction, Transaction> attributeData;
    ICustomerRepository customerRepository;


    @Override
    public Page<Transaction> getAll(FindTransaction requestParam, PageOption o) {
        return repository.findAll(attributeData.getAttribute(requestParam), hp.dataPageOption(o));
    }

    @Override
    public Transaction findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ApiException(ErrorCode.GENERAL_NOT_FOUND));
    }

    @Override
    public Transaction create(Transaction data) {
        this.checkUpdateQuantity(data, null);
        data.setTransactionDate(LocalDateTime.now());
        this.autoSetColCreate(data);
        this.convertEmail(data);
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
        Transaction tr = this.findById(id);
        this.checkUpdateQuantity(newData, tr);
        newData.setId(id);
        this.autoSetColUpdate(newData);
        this.convertEmail(newData);
        return repository.save(newData);
    }

    @Override
    public Boolean changeIsPurchase(Long id) {
        Transaction tr = this.findById(id);
        this.autoSetCol(tr);
        repository.save(tr);
        return true;
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

    private void autoSetColCreate(Transaction tr) {
        Book b = bS.findById(tr.getBook().getId());
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
        bS.update(b.getId(), b);
    }

    private void autoSetColUpdate(Transaction tr) {
        Transaction t = this.findById(tr.getId());
        Book b = bS.findById(tr.getBook().getId());
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

    private void checkUpdateQuantity(Transaction newData, Transaction oldData) {
        Book b = bS.findById(newData.getBook().getId());

        long bookDifference = (oldData == null)
                ? newData.getNumberOfBooks()
                : newData.getNumberOfBooks() - oldData.getNumberOfBooks();

        if (bookDifference > 0 && b.getQuantity() < bookDifference) {
            throw new ApiException(ErrorCode.CREATE_FAILED, Map.of("Book", "Book in stock not enough"));
        }

        b.setQuantity(b.getQuantity() - bookDifference);
        bS.update(b.getId(), b);
    }

    private void convertEmail(Transaction data) {
        Customer c  = customerRepository.findByEmail(data.getCustomer().getEmail());
        data.getCustomer().setId(c.getId());
    }
}
