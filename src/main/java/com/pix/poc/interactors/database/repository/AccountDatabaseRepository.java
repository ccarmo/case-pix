package com.pix.poc.interactors.database.repository;

import com.pix.poc.domain.entities.Account;
import com.pix.poc.domain.repository.AccountRepository;
import com.pix.poc.domain.vo.AccountNumber;
import com.pix.poc.domain.vo.AgencyNumber;
import com.pix.poc.domain.vo.Document;
import com.pix.poc.interactors.database.mapper.AccountMapper;
import com.pix.poc.interactors.database.model.AccountId;
import com.pix.poc.interactors.database.model.AccountModel;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class AccountDatabaseRepository implements AccountRepository {

    AccountJpaRepository accountJpaRepository;
    AccountMapper accountMapper;

    public AccountDatabaseRepository(AccountJpaRepository accountJpaRepository, AccountMapper accountMapper) {
        this.accountJpaRepository = accountJpaRepository;
        this.accountMapper = accountMapper;
    }


    @Override
    public List<Account> getAccountsByDocument(Document document) {
        List<AccountModel> list = accountJpaRepository.findAllByDocument(document.getValue());
        return list.stream()
                .map(accountModel -> accountMapper.toDomain(accountModel))
                .toList();
    }

    @Override
    public Optional<Account> findByAccountNumberAndAgencyNumber(AccountNumber accountNumber, AgencyNumber agencyNumber) {
        Optional<AccountModel> optionalAccountModel = accountJpaRepository.findByIdAccountNumberAndIdAgencyNumber(accountNumber.getValue(), agencyNumber.getValue());
        return optionalAccountModel.map(accountModel -> accountMapper.toDomain(accountModel));
    }

    @Override
    public void save(Account account) {
        try {
            AccountModel accountModel = accountMapper.toModel(account);
            accountJpaRepository.saveAndFlush(accountModel);
        } catch (Exception ex) {
            throw ex;
        }
    }
}
