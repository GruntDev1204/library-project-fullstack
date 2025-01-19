package com.library_management.api.service.impl.TransactionSV;

import com.library_management.api.attribute.attribute_class.FindTransaction;
import com.library_management.api.attribute.pagination.PageOption;
import com.library_management.api.model.Transaction;
import com.library_management.api.service.InterfaceService;
import org.springframework.data.domain.Page;

public interface IAdditionalTransaction extends InterfaceService<Transaction, FindTransaction, Page<Transaction>, PageOption> {
    Boolean changeIsPurchase(Long id);
}
