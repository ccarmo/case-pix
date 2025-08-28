package com.pix.poc.infra.database.mapper;

import com.pix.poc.domain.entities.Account;
import com.pix.poc.domain.entities.AccountType;
import com.pix.poc.domain.entities.Pix;
import com.pix.poc.domain.entities.PixType;
import com.pix.poc.domain.vo.*;
import com.pix.poc.infra.database.model.AccountId;
import com.pix.poc.infra.database.model.AccountModel;
import com.pix.poc.infra.database.model.PixModel;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.Objects;

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
                pix.getUniqueID().value(),
                pix.getPixType().name(),
                pix.getPixValue().getValue(),
                accountModel,
                pix.getInclusionDate().toInstant(),
                Objects.nonNull(pix.getInactivationDate()) ? pix.getInactivationDate().toInstant() : null,
                pix.isActive()
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
                .uniqueID(new PixId(model.getId()))
                .account(account)
                .inactivationDate(Objects.nonNull(model.getInactivationDate()) ?  model.getInactivationDate().atZone(ZoneId.of("America/Sao_Paulo")) : null)
                .inclusionDate(Objects.nonNull(model.getInclusionDate()) ? model.getInclusionDate().atZone(ZoneId.of("America/Sao_Paulo")) : null)
                .pixType(PixType.fromString(model.getPixType()))
                .pixValue(new PixValue(model.getPixValue(), PixType.fromString(model.getPixType())))
                .active(model.getActive())
                .build();
    }
}
