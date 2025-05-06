package com.library_management.api.repository;

import com.library_management.api.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAccountRepository extends JpaRepository<Account, Long>{
    Account findByUserName(String userName);
}
