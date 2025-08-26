package com.pix.poc.interactors.database.mapper;


import com.pix.poc.domain.entities.Account;
import com.pix.poc.domain.entities.AccountType;
import com.pix.poc.domain.vo.AccountNumber;
import com.pix.poc.domain.vo.AgencyNumber;
import com.pix.poc.domain.vo.Document;
import com.pix.poc.interactors.database.model.AccountId;
import com.pix.poc.interactors.database.model.AccountModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountMapper {

    private AccountMapper() {}

    public Account toDomain(AccountModel model) {
        if (model == null) return null;

        AccountId id = model.getId();

        return new Account.Builder()
                .accountNumber(new AccountNumber(id.getAccountNumber()))
                .agencyNumber(new AgencyNumber(id.getAgencyNumber()))
                .accountType(model.getAccountType() != null ? AccountType.valueOf(model.getAccountType()) : null)
                .name(model.getName())
                .lastName(model.getLastName())
                .document(new Document(model.getDocumentNumber()))
                .build();
    }

    public AccountModel toModel(Account account) {
        if (account == null) return null;

        return new AccountModel(
                new AccountId(
                        account.getAccountNumber().getValue(),
                        account.getAgencyNumber().getValue()
                ),
                account.getAccountType().name(),
                account.getName(),
                account.getLastName(),
                account.getDocument().getValue()
        );
    }

    public AccountId toAccountId(Account account) {

        Integer accountNumber = account.getAccountNumber() != null
                ? account.getAccountNumber().getValue()
                : null;
        Integer agencyNumber = account.getAgencyNumber() != null
                ? account.getAgencyNumber().getValue()
                : null;

        return new AccountId(accountNumber, agencyNumber);
    }

    public List<AccountId> toAccountIdList(List<Account> accounts) {
        if (accounts == null) return List.of();
        return accounts.stream()
                .map(this::toAccountId)
                .collect(Collectors.toList());
    }
}

