package com.library_management.api.service.impl;

import com.library_management.api.config.EnvClass;
import com.library_management.api.dto.auth_process.TokenManagement;
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
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;

@Service
@AllArgsConstructor
@Primary
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerService implements InterfaceAuthService<AuthReq, AuthRes, CustomerReq, CustomerRes, Customer> {
    ICustomerRepository infoRepo;
    IAccountRepository accountRepo;
    TokenManagement tk;

    private String encodePassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        return passwordEncoder.encode(password);
    }

    private Customer createCustomer(CustomerReq data) {
        Customer c = new Customer();
        c.setFullName(data.getFullName());
        c.setPhoneNumber(data.getPhoneNumber());
        c.setEmail(data.getEmail());
        return infoRepo.save(c);
    }

    private Account createAccount(CustomerReq data, Customer customer) {
        Account a = new Account();
        a.setUserName(data.getUserName());
        a.setPassword(this.encodePassword(data.getPassword()));
        a.setStatus("ACTIVE");
        a.setCustomer(customer);
        return accountRepo.save(a);
    }

    private void checkExist(CustomerReq data) {
        Account exist = accountRepo.findByUserName(data.getUserName());
        Customer existC = infoRepo.findByEmail(data.getEmail());
        if (exist != null) {
            throw new ApiException(ErrorCode.Exist_UserName);
        } else if (existC != null) {
            throw new ApiException(ErrorCode.Exist_Email);
        }
    }

    @NotNull
    private CustomerRes getCustomerRes(Account user) {
        CustomerRes res = new CustomerRes();
        res.setStatus(user.getStatus());
        res.setUserName(user.getUserName());
        res.setFullName(user.getCustomer().getFullName());
        res.setPhoneNumber(user.getCustomer().getPhoneNumber());
        res.setEmail(user.getCustomer().getEmail());
        return res;
    }

    @Override
    public CustomerRes register(CustomerReq data) {
        this.checkExist(data);
        Customer customer = this.createCustomer(data);
        Account account = this.createAccount(data, customer);
        return this.getCustomerRes(account);
    }

    @Override
    public AuthRes login(AuthReq data) {
        Account user = accountRepo.findByUserName(data.getUserName());
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        if (user == null) {
            throw new ApiException(ErrorCode.User_Not_Found);
        }
        if (!passwordEncoder.matches(data.getPassword(), user.getPassword())) {
            throw new ApiException(ErrorCode.Wrong_Password);
        }
        String token = tk.createToken(user);
        return new AuthRes(token);
    }

    @Override
    public Boolean checkToken(String token) throws JOSEException, ParseException {
        return tk.checkToken(token);
    }

    @Override
    public Void logout(String token) throws JOSEException, ParseException {
        Boolean isValid = this.checkToken(token);
        if (!isValid) {
            throw new ApiException(ErrorCode.Authentication_is_not_ok);
        }
        return null;
    }

    @Override
    public CustomerRes getProfile(String token) throws JOSEException, ParseException {
        this.checkToken(token);
        SignedJWT signedJWT = tk.analyzeToken(token);

        String username = signedJWT.getJWTClaimsSet().getSubject();
        Account user = accountRepo.findByUserName(username);

        if (user == null) {
            throw new ApiException(ErrorCode.User_Not_Found);
        }
        return this.getCustomerRes(user);
    }

    @Override
    public Customer updateProfile(Customer data) {
        return null;
    }
}
