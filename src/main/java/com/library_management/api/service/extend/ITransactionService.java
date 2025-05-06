package com.library_management.api.service.extend;

import com.library_management.api.dto.transaction.TransactionReq;
import com.library_management.api.dto.transaction.TransactionRes;
import com.library_management.api.helper.filter_cols.FindTransaction;
import com.library_management.api.helper.pagination.PageOption;
import com.library_management.api.service.InterfaceService;
import org.springframework.data.domain.Page;

public interface ITransactionService extends InterfaceService<TransactionReq, TransactionRes> {
    Page<TransactionRes> getAll(FindTransaction filter , PageOption pageOption);
    Boolean changeIsPurchase(Long id);
}
