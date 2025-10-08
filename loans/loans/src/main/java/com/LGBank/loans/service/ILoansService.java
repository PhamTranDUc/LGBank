package com.LGBank.loans.service;

import com.LGBank.loans.dto.LoansDto;

public interface ILoansService {

    void createNew(String mobileNumber);
    boolean update(LoansDto loansDto);
    LoansDto fetchByPhoneNumber(String phoneNumber);
    LoansDto fetchByLoansNumber(String loansNumber);
    boolean deleteByPhoneNumber(String phoneNumber);
}
