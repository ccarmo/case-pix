package com.pix.poc.interactors.database.mapper;

import com.pix.poc.domain.entities.Account;
import com.pix.poc.domain.entities.AccountType;
import com.pix.poc.domain.entities.Pix;
import com.pix.poc.domain.entities.PixType;
import com.pix.poc.domain.vo.AccountNumber;
import com.pix.poc.domain.vo.AgencyNumber;
import com.pix.poc.domain.vo.Document;
import com.pix.poc.domain.vo.PixValue;
import com.pix.poc.interactors.database.model.AccountId;
import com.pix.poc.interactors.database.model.AccountModel;
import com.pix.poc.interactors.database.model.PixModel;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PixMapper {

    private PixMapper() {}

    public PixModel toModel(Pix pix) {
        Account account = pix.getAccount();

        AccountModel accountModel = new AccountModel(
                new AccountId(
                        account.getAccountNumber().getValue(),
                        account.getAgencyNumber().getValue()
                ),
                account.getAccountType() != null ? account.getAccountType().name() : null,
                account.getName(),
                account.getLastName(),
                account.getDocument().getValue()
        );

        return new PixModel(
                pix.getUniqueID(),
                pix.getPixType().name(),
                pix.getPixValue().getValue(),
                accountModel,
                LocalDate.now(),
                null
        );
    }


    public Pix toDomain(PixModel model) {
        AccountModel accountModel = model.getAccount();

        Account account = new Account.Builder()
                .document(new Document(accountModel.getDocumentNumber()))
                .accountNumber(new AccountNumber(accountModel.getId().getAccountNumber()))
                .agencyNumber(new AgencyNumber(accountModel.getId().getAgencyNumber()))
                .name(accountModel.getName())
                .lastName(accountModel.getLastName())
                .accountType(accountModel.getAccountType() != null ? AccountType.valueOf(accountModel.getAccountType()) : null)
                .build();

        return new Pix.Builder()
                .uniqueID(model.getId())
                .account(account)
                .inactivationDate(model.getInactivationDate())
                .inclusionDate(model.getInclusionDate())
                .pixType(PixType.valueOf(model.getPixType()))
                .pixValue(new PixValue(model.getPixValue(), PixType.valueOf(model.getPixType())))
                .build();
    }
}
