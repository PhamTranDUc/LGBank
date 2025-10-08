package com.LGBank.accounts.mapper;

import com.LGBank.accounts.dto.AccountDto;
import com.LGBank.accounts.entity.Accounts;

public class AccountMapper {

    public static Accounts toAccount(Accounts accounts, AccountDto accountDto){
        accounts.setAccountNumber(accountDto.getAccountNumber());
        accounts.setAccountType(accountDto.getAccountType());
        accounts.setBranchAddress(accountDto.getBranchAddress());
        return accounts;
    }

    public static AccountDto toAccountDto(Accounts accounts, AccountDto accountDto){
        accountDto.setAccountNumber(accounts.getAccountNumber());
        accountDto.setAccountType(accounts.getAccountType());
        accountDto.setBranchAddress(accounts.getBranchAddress());
        return accountDto;
    }
}
