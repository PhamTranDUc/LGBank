package com.LGBank.accounts.message;

import com.LGBank.accounts.dto.AccountDto;
import com.LGBank.accounts.dto.AccountsMsgDto;
import com.LGBank.accounts.service.IAccountService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class AccountConsumer {

    @Bean
    public Consumer<AccountsMsgDto> updateStatusAccount(IAccountService iAccountService){
       return accountsMsgDto -> {
          iAccountService.activeAccount(accountsMsgDto.mobileNumber());
       };
    }
}
