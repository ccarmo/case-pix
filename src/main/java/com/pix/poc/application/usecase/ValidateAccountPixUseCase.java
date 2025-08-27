package com.pix.poc.application.usecase;

import com.pix.poc.domain.entities.Account;
import com.pix.poc.domain.vo.AccountNumber;
import com.pix.poc.domain.vo.AgencyNumber;

public interface ValidateAccountPixUseCase {
    Account validateAccount(AccountNumber accountNumber,AgencyNumber agencyNumber);
}
