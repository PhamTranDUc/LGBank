package com.LGBank.accounts.controller;

import com.LGBank.accounts.constants.AccountsConstants;
import com.LGBank.accounts.dto.AccountsContactInfoDto;
import com.LGBank.accounts.dto.CustomerDto;
import com.LGBank.accounts.dto.ResponseDto;
import com.LGBank.accounts.service.IAccountService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping(path = "/api")
public class AccountController {

    private IAccountService accountService;

    @Autowired
    private AccountsContactInfoDto accountsContactInfoDto;

    @Autowired
    private Environment environment;

    public AccountController(IAccountService accountService){
        this.accountService= accountService;
    }

    @Value("${build.version}")
    private String buildVersion;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto){
        accountService.createAccount(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(AccountsConstants.STATUS_201,AccountsConstants.MESSAGE_201));
    }

    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetail(@RequestParam
                          @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                          String mobileNumber){
        CustomerDto customerDto= accountService.fetchAccountDetail(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto){
        boolean isUpdated= accountService.updateAccount(customerDto);
        if(isUpdated){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200));
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountsConstants.STATUS_500, AccountsConstants.MESSAGE_500));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetail(@RequestParam
                           @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                           String mobileNumber){
        boolean isDeleted =accountService.deleteAccount(mobileNumber);
        if(isDeleted){
            return  ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200,AccountsConstants.MESSAGE_200));
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountsConstants.STATUS_500,AccountsConstants.MESSAGE_500));
        }
    }

    @GetMapping("/build-infor")
    public ResponseEntity<AccountsContactInfoDto> getInfor(){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(accountsContactInfoDto);
        }

}
