package com.library_management.api.service.impl;

import com.library_management.api.dto.code_status.impl.ErrorCode;
import com.library_management.api.dto.exception.ApiException;
import com.library_management.api.dto.req_and_res.authentication.AuthReq;
import com.library_management.api.dto.req_and_res.authentication.AuthRes;
import com.library_management.api.dto.req_and_res.customer.CustomerReq;
import com.library_management.api.dto.req_and_res.customer.CustomerRes;
import com.library_management.api.model.Account;
import com.library_management.api.model.Customer;
import com.library_management.api.repository.IAccountRepository;
import com.library_management.api.repository.ICustomerRepository;
import com.library_management.api.service.InterfaceAuthService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
@Primary
public class CustomerService implements InterfaceAuthService<AuthReq, AuthRes, CustomerReq, CustomerRes, Customer> {
    ICustomerRepository repositoryC;
    IAccountRepository repositoryA;

    private String encodePassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        return passwordEncoder.encode(password);
    }

    private void createAccount(CustomerReq data, Customer customer) {
        Account a = new Account();

        a.setUserName(data.getUserName());
        a.setPassword(this.encodePassword(data.getPassword()));
        a.setStatus("ACTIVE");
        a.setCustomer(customer);
        repositoryA.save(a);
    }

    private Customer createCustomer(CustomerReq data) {
        Customer c = new Customer();
        c.setFullName(data.getFullName());
        c.setPhoneNumber(data.getPhoneNumber());
        c.setEmail(data.getEmail());
        return repositoryC.save(c);
    }

    private void checkExist(CustomerReq data) {
        Account exist = repositoryA.findByUserName(data.getUserName());
        Customer existC = repositoryC.findByEmail(data.getEmail());
        if (exist != null) {
            throw new ApiException(ErrorCode.Exist_UserName);
        } else if (existC != null) {
            throw new ApiException(ErrorCode.Exist_Email);
        }
    }

    @Override
    public CustomerRes register(CustomerReq data) {
        this.checkExist(data);

        Customer customer = this.createCustomer(data);
        this.createAccount(data, customer);

        CustomerRes res = new CustomerRes();
        res.setUserName(data.getUserName());
        res.setStatus("ACTIVE");
        res.setFullName(data.getFullName());
        res.setPhoneNumber(data.getPhoneNumber());
        res.setEmail(data.getEmail());
        return res;
    }

    @Override
    public AuthRes login(AuthReq data) {
        return null;
    }

    @Override
    public Customer getProfile(String username) {
        return null;
    }

    @Override
    public Customer updateProfile(Customer data) {
        return null;
    }

    @Override
    public Boolean checkToken(String token) {
        return null;
    }


}
