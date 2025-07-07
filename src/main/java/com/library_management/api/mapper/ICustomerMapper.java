package com.library_management.api.mapper;

import com.library_management.api.dto.customer.CustomerRes;
import com.library_management.api.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ICustomerMapper {
    @Mapping(source = "status", target = "status")
    @Mapping(source = "userName", target = "userName")
    @Mapping(source = "customer.fullName", target = "fullName")
    @Mapping(source = "customer.phoneNumber", target = "phoneNumber")
    @Mapping(source = "customer.email", target = "email")
    @Mapping(source = "is2FA", target = "is2FA")
    CustomerRes entityToRes(Account account);
}
