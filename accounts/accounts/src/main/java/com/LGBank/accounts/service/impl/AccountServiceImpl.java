package com.LGBank.accounts.service.impl;

import com.LGBank.accounts.constants.AccountsConstants;
import com.LGBank.accounts.dto.AccountDto;
import com.LGBank.accounts.dto.CustomerDto;
import com.LGBank.accounts.entity.Accounts;
import com.LGBank.accounts.entity.Customer;
import com.LGBank.accounts.exceptions.CustomerAlreadyExistsException;
import com.LGBank.accounts.exceptions.ResourceNotFoundException;
import com.LGBank.accounts.mapper.AccountMapper;
import com.LGBank.accounts.mapper.CustomerMapper;
import com.LGBank.accounts.repository.AccountRepository;
import com.LGBank.accounts.repository.CustomerRepository;
import com.LGBank.accounts.service.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customerSaved = CustomerMapper.toCustomer(new Customer(),customerDto);
        Optional<Customer> optionalCustomer= customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if(optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber "
                    +customerDto.getMobileNumber());
        }
        customerRepository.save(customerSaved);
        Accounts accounts =createNewAccount(customerSaved);
        accountRepository.save(accounts);

    }

    @Override
    public CustomerDto fetchAccountDetail(String mobileNumber) {
        Customer customer= customerRepository.findByMobileNumber(mobileNumber).orElseThrow(()->
                new ResourceNotFoundException("Customer","MobileNumber",mobileNumber)
        );
       Accounts accounts = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(() ->
                new ResourceNotFoundException("Customer","MobileNumber",mobileNumber)
        );
       AccountDto accountDtoResult= AccountMapper.toAccountDto(accounts,new AccountDto());
       CustomerDto customerDtoResult = CustomerMapper.toCustomerDto(customer,new CustomerDto());
        customerDtoResult.setAccountDto(accountDtoResult);
        return customerDtoResult;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountDto accountDto = customerDto.getAccountDto();
        if(accountDto != null){
            Accounts accounts = accountRepository.findById(accountDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account","AccountNumber",accountDto.getAccountNumber().toString())
            );
            AccountMapper.toAccount(accounts,accountDto);
            accounts = accountRepository.save(accounts);
            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer","CustomerID",customerId.toString())
            );
            CustomerMapper.toCustomer(customer,customerDto);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        boolean isDeleted= false;
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer","MobileNumber",mobileNumber)
        );
        customerRepository.deleteById(customer.getCustomerId());
        accountRepository.deleteByCustomerId(customer.getCustomerId());
        isDeleted = true;
        return isDeleted;
    }

    private Accounts createNewAccount(Customer customer){
        Accounts newAccounts= new Accounts();
        long randomAccNumber = 100000000000L + new Random().nextLong(900000000000L);
        newAccounts.setAccountNumber(randomAccNumber);
        newAccounts.setCustomerId(customer.getCustomerId());
        newAccounts.setBranchAddress(AccountsConstants.ADDRESS);
        newAccounts.setAccountType(AccountsConstants.SAVINGS);
        return newAccounts;
    }
}
