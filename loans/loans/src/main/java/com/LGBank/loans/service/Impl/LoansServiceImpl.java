package com.LGBank.loans.service.Impl;

import com.LGBank.loans.constants.LoansConstants;
import com.LGBank.loans.dto.LoansDto;
import com.LGBank.loans.entity.Loans;
import com.LGBank.loans.exception.LoanAlreadyExistsException;
import com.LGBank.loans.exception.ResourceNotFoundException;
import com.LGBank.loans.mapper.LoansMapper;
import com.LGBank.loans.repository.LoansRepository;
import com.LGBank.loans.service.ILoansService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class LoansServiceImpl implements ILoansService {

    public LoansRepository loansRepository;


    @Override
    public void createNew(String mobileNumber) {
       Optional<Loans> optionalLoans= loansRepository.findByMobileNumber(mobileNumber);
       if (optionalLoans.isPresent()){
           throw new LoanAlreadyExistsException("Loan already registered with given mobileNumber "+mobileNumber);
       }
       loansRepository.save(createNewLoan(mobileNumber));

    }

    private Loans createNewLoan(String mobileNumber) {
        Loans newLoan = new Loans();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoansConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0L);
        newLoan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);
        return newLoan;
    }

    @Override
    public boolean update(LoansDto loansDto) {
        boolean isUpdate = false;
        if (loansDto != null){
            Loans loans = loansRepository.findByLoanNumber(loansDto.getLoanNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Loans","LoansNumber",loansDto.getLoanNumber())
            );
            loansRepository.save(LoansMapper.mapToLoans(loansDto,loans));
            isUpdate = true;
        }
        return isUpdate;
    }

    @Override
    public LoansDto fetchByPhoneNumber(String phoneNumber) {
        Optional<Loans> optionalLoans= loansRepository.findByMobileNumber(phoneNumber);
        if (optionalLoans.isEmpty()){
          throw new ResourceNotFoundException("Loans","MobileNumber",phoneNumber);
        }
        return LoansMapper.mapToLoansDto(optionalLoans
                .get(), new LoansDto()) ;
    }

    @Override
    public LoansDto fetchByLoansNumber(String loansNumber) {
        Optional<Loans> optionalLoans= loansRepository.findByLoanNumber(loansNumber);
        if (optionalLoans.isEmpty()){
            throw new ResourceNotFoundException("Loans","LoansNumber",loansNumber);
        }
        return LoansMapper.mapToLoansDto(optionalLoans
                .get(), new LoansDto()) ;
    }

    @Override
    public boolean deleteByPhoneNumber(String phoneNumber) {
        Loans loans = loansRepository.findByMobileNumber(phoneNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "MobileNumber", phoneNumber)
        );
        loansRepository.deleteById(loans.getLoanId());
        return true;
    }
}

