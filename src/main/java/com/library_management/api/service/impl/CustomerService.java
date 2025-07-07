package com.library_management.api.service.impl;

import com.library_management.api.dto.authentication.*;
import com.library_management.api.helper.auth_process.AESEncoder;
import com.library_management.api.helper.auth_process.TOTPHelper;
import com.library_management.api.helper.auth_process.AuthHelper;
import com.library_management.api.helper.code_status.ErrorCode;
import com.library_management.api.exception.ApiException;
import com.library_management.api.dto.authentication.AuthRes;
import com.library_management.api.dto.customer.CustomerReq;
import com.library_management.api.dto.customer.CustomerRes;
import com.library_management.api.mapper.ICustomerMapper;
import com.library_management.api.model.Account;
import com.library_management.api.model.Customer;
import com.library_management.api.repository.IAccountRepository;
import com.library_management.api.repository.ICustomerRepository;
import com.library_management.api.service.InterfaceAuthService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
@Primary
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerService implements InterfaceAuthService<AuthReq, AuthRes, CustomerReq, CustomerRes> {
    ICustomerRepository infoRepo;
    IAccountRepository accountRepo;
    ICustomerMapper mapper;

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
        a.setPassword(AuthHelper.encodePassword(data.getPassword() , null));
        a.setStatus("ACTIVE");
        a.setCustomer(customer);
        return accountRepo.save(a);
    }

    private Account checkExist(CustomerReq data , Authentication auth , AuthReq authReq , String option) {
        String username = auth != null ? auth.getName()
                : (authReq != null ? authReq.getUserName()
                : data.getUserName());

        Account account = accountRepo.findByUserName(username);
        Customer customer = data != null ? infoRepo.findByEmail(data.getEmail()) : null;

        switch (option) {
           case "unique" -> {
               if (account != null) throw new ApiException(ErrorCode.Exist_UserName);
               if (customer != null) throw new ApiException(ErrorCode.Exist_Email);
               return null;
           }
           case "not_exist" -> {
               if (account == null) {
                   throw new ApiException(ErrorCode.User_Not_Found);
               }
               return account;
           }
            default -> throw new IllegalArgumentException("Invalid option");
        }
    }

    @Override
    public CustomerRes register(CustomerReq data) {
        this.checkExist(data , null, null, "unique");
        Customer customer = this.createCustomer(data);
        Account account = this.createAccount(data, customer);
        return mapper.entityToRes(account);
    }

    @Override
    public AuthRes login(AuthReq data) {
        Account user = this.checkExist( null , null , data, "not_exist");
        assert user != null;

        AuthHelper.verifyPassword(data.getPassword() , user.getPassword() , null);

        if(user.getIs2FA()) {
            if(data.getOtp() != null)  TOTPHelper.verifyTOTP(AESEncoder.decode(user.getSecretKey()), data.getOtp());
            else throw new ApiException(ErrorCode.OTP_REQUIRED);
        }

        String token = AuthHelper.createToken(user, "customer");
        return new AuthRes(token);
    }

    @Override
    public Void logout(String token) {
        boolean isValid = false;
        if (!isValid) {
            throw new ApiException(ErrorCode.Authentication_is_not_ok);
        }
        return null;
    }

    @Override
    public CustomerRes getProfile(Authentication auth) {
        Account user = this.checkExist(null , auth , null,"not_exist");
        String role = AuthHelper.getRole(auth);

        CustomerRes response = mapper.entityToRes(user);
        response.setRole(role);
        return response;
    }

    @Override
    public CustomerRes updateProfile(Authentication auth , CustomerReq data) {
        CustomerRes res = this.getProfile(auth);
        Customer dataUpdate = infoRepo.findByEmail(res.getEmail());

        dataUpdate.setFullName(data.getFullName());
        dataUpdate.setPhoneNumber(data.getPhoneNumber());
        infoRepo.save(dataUpdate);

        res.setFullName(data.getFullName());
        res.setFullName(data.getFullName());
        return res;
    }

    @Override
    public String enable2FA(Authentication auth){
        Account user = this.checkExist(null , auth , null,"not_exist");
        assert user != null;
        if (user.getIs2FA()) {
            return "2FA is already enabled!";
        }else {
            user.setIs2FA(true);

            String originKey = TOTPHelper.generateSecretKey();
            user.setSecretKey(AESEncoder.encode(originKey));
            accountRepo.save(user);

            return TOTPHelper.generateQr(originKey, user.getUserName());
        }
    }
}
