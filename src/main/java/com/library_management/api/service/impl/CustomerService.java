package com.library_management.api.service.impl;

import com.library_management.api.dto.authentication.*;
import com.library_management.api.helper.auth_process.AESEncoder;
import com.library_management.api.helper.auth_process.TOTPHelper;
import com.library_management.api.helper.auth_process.TokenManagement;
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
import com.nimbusds.jose.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
@AllArgsConstructor
@Primary
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerService implements InterfaceAuthService<AuthReq, AuthRes, CustomerReq, CustomerRes> {
    ICustomerRepository infoRepo;
    IAccountRepository accountRepo;
    ICustomerMapper mapper;
    TokenManagement tk;

    private String encodePassword(String password) {
        return new BCryptPasswordEncoder(10).encode(password);
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

    @Override
    public CustomerRes register(CustomerReq data) {
        this.checkExist(data);
        Customer customer = this.createCustomer(data);
        Account account = this.createAccount(data, customer);
        return mapper.entityToRes(account);
    }

    @Override
    public AuthRes login(AuthReq data) throws ParseException, JOSEException {
        Account user = accountRepo.findByUserName(data.getUserName());
        if (user == null) {
            throw new ApiException(ErrorCode.User_Not_Found);
        }

        if (!new BCryptPasswordEncoder(10).matches(data.getPassword(), user.getPassword())) {
            throw new ApiException(ErrorCode.Wrong_Password);
        }

        if(user.getIs2FA()) {
            if(data.getOtp() != null)  TOTPHelper.verifyTOTP(AESEncoder.decode(user.getSecretKey()), data.getOtp());
            else throw new ApiException(ErrorCode.OTP_REQUIRED);
        }

        String token = tk.createToken(user, "customer");
        return new AuthRes(token);
    }

    @Override
    public Void logout(String token) throws JOSEException, ParseException {
        boolean isValid = false;
        if (!isValid) {
            throw new ApiException(ErrorCode.Authentication_is_not_ok);
        }
        return null;
    }

    @Override
    public CustomerRes getProfile(Authentication auth) {
        Account user = accountRepo.findByUserName(auth.getName());
        String role = tk.getRole(auth);

        if (user == null) {
            throw new ApiException(ErrorCode.User_Not_Found);
        }

        CustomerRes response = mapper.entityToRes(user);
        response.setRole(role);
        return response;
    }

    @Override
    public CustomerRes updateProfile(Authentication auth , CustomerReq data) throws ParseException, JOSEException {
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
    public String enable2FA(Authentication auth) throws ParseException, JOSEException {
        Account user = accountRepo.findByUserName(auth.getName());
        if (user == null) {
            throw new ApiException(ErrorCode.User_Not_Found);
        }

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
