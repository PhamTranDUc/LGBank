package com.LGBank.accounts.service.client;

import com.LGBank.accounts.dto.LoansDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LoansFallBack implements LoansFeignClient{
    @Override
    public ResponseEntity<LoansDto> fetchLoanDetail(String correlationId, String mobileNumber) {
        return null;
    }
}
