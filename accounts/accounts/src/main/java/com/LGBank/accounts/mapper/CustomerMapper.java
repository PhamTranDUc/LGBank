package com.LGBank.accounts.mapper;

import com.LGBank.accounts.dto.CustomerDto;
import com.LGBank.accounts.entity.Customer;

public class CustomerMapper {

    public static Customer toCustomer(Customer customer, CustomerDto customerDto){
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setMobileNumber(customerDto.getMobileNumber());
        return  customer;
    }

    public static CustomerDto toCustomerDto(Customer customer, CustomerDto customerDto){
        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setMobileNumber(customer.getMobileNumber());
        return  customerDto;
    }
}
