package com.pix.poc.domain.repository;

import com.pix.poc.domain.entities.Account;
import com.pix.poc.domain.vo.AccountNumber;
import com.pix.poc.domain.vo.AgencyNumber;
import com.pix.poc.domain.vo.Document;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {

    List<Account> getAccountsByDocument(Document document);
    Optional<Account> findByAccountNumberAndAgencyNumber(AccountNumber accountNumber, AgencyNumber agencyNumber);
    void save(Account account);
}
