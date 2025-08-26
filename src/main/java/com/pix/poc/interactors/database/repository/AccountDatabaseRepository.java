package com.pix.poc.interactors.database.repository;

import com.pix.poc.domain.entities.Account;
import com.pix.poc.domain.repository.AccountRepository;
import com.pix.poc.domain.vo.Document;
import com.pix.poc.interactors.database.mapper.AccountMapper;
import com.pix.poc.interactors.database.model.AccountModel;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
