package com.LGBank.accounts.service.impl;

import com.LGBank.accounts.dto.AccountDto;
import com.LGBank.accounts.dto.CardsDto;
import com.LGBank.accounts.dto.CustomerDetailsDto;
import com.LGBank.accounts.dto.LoansDto;
import com.LGBank.accounts.entity.Accounts;
import com.LGBank.accounts.entity.Customer;
import com.LGBank.accounts.exceptions.ResourceNotFoundException;
import com.LGBank.accounts.mapper.AccountMapper;
import com.LGBank.accounts.mapper.CustomerMapper;
import com.LGBank.accounts.repository.AccountRepository;
import com.LGBank.accounts.repository.CustomerRepository;
import com.LGBank.accounts.service.ICustomerService;
import com.LGBank.accounts.service.client.CardsFeignClient;
import com.LGBank.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;
    private LoansFeignClient loansFeignClient;
    private CardsFeignClient cardsFeignClient;


    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "MobileNumber", mobileNumber)
        );

        Accounts accounts = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account","customerId", customer.getCustomerId().toString())
        );

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountDto(AccountMapper.toAccountDto(accounts, new AccountDto()));
        ResponseEntity<LoansDto> loansDto = loansFeignClient.fetchLoanDetail(correlationId,mobileNumber);
        if(loansDto != null){
            customerDetailsDto.setLoansDto(loansDto.getBody());
        }
        ResponseEntity<CardsDto> cardsDto = cardsFeignClient.fetchCardDetails(correlationId,mobileNumber);
        if (cardsDto != null){
            customerDetailsDto.setCardsDto(cardsDto.getBody());
        }
        return customerDetailsDto;
    }
}
