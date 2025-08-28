package com.pix.poc.infra.database.repository;

import com.pix.poc.domain.entities.Account;
import com.pix.poc.domain.entities.Pix;

import com.pix.poc.domain.repository.PixRepository;

import com.pix.poc.infra.database.mapper.AccountMapper;
import com.pix.poc.infra.database.mapper.PixMapper;
import com.pix.poc.infra.database.model.AccountId;
import com.pix.poc.infra.database.model.AccountModel;
import com.pix.poc.infra.database.model.PixModel;

import jakarta.persistence.criteria.Expression;

import org.springframework.stereotype.Service;


import java.time.LocalDate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class PixDatabaseRepository implements PixRepository {

    PixJpaRepository pixJpaRepository;
    AccountJpaRepository accountJpaRepository;
    AccountMapper accountMapper;
    PixMapper pixMapper;

    public PixDatabaseRepository(PixJpaRepository pixJpaRepository,
                                 PixMapper pixMapper,
                                 AccountMapper accountMapper,
                                 AccountJpaRepository accountJpaRepository) {
        this.pixJpaRepository = pixJpaRepository;
        this.accountMapper = accountMapper;
        this.pixMapper = pixMapper;
        this.accountJpaRepository = accountJpaRepository;
    }

    @Override
    public Pix save(Pix pix) {
        try {
            PixModel pixModel = pixMapper.toModel(pix);
            pixJpaRepository.save(pixModel);
            return pix;
        } catch (Exception ex) {
            throw ex;
        }

    }

    @Override
    public List<Pix> get(String id, String pixType, String uniqueId, Integer agencyNumber, Integer accountNumber, String name, LocalDate inclusionDate, LocalDate inactivationDate) {
        try {

            if(id != null) {
                Optional<PixModel> pixModel = pixJpaRepository.findById(id);

                if (pixModel.isEmpty()) {
                    return Collections.emptyList();
                }

                Pix pix = pixMapper.toDomain(pixModel.get());
                return List.of(pix);
            }

            List<Pix> listPix = this.findByDynamicFilter(
                    pixType,
                    agencyNumber,
                    accountNumber,
                    name,
                    inclusionDate,
                    inactivationDate
            );

            return listPix;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Pix> findByDynamicFilter(
            String pixType,
            Integer agencyNumber,
            Integer accountNumber,
            String accountHolderName,
            LocalDate inclusionDate,
            LocalDate inactivationDate
    ) {
        try {
            List<PixModel> listPixModel = pixJpaRepository.findAll((root, query, cb) -> {

                var predicates = cb.conjunction();

                // Filtros diretos da tabela PIX
                if (pixType != null && !pixType.isEmpty()) {
                    predicates = cb.and(predicates, cb.equal(root.get("pixType"), pixType));
                }

                if (inclusionDate != null) {
                    Expression<LocalDate> dateOnly = cb.function("date", LocalDate.class, root.get("inclusionDate"));
                    predicates = cb.and(predicates, cb.greaterThanOrEqualTo(dateOnly, inclusionDate));
                }

                if (inactivationDate != null) {
                    Expression<LocalDate> dateOnly = cb.function("date", LocalDate.class, root.get("inactivationDate"));
                    predicates = cb.and(predicates, cb.lessThanOrEqualTo(dateOnly, inactivationDate));
                }

                // Filtros de Account
                if (agencyNumber != null) {
                    predicates = cb.and(predicates, cb.equal(root.get("account").get("id").get("agencyNumber"), agencyNumber));
                }

                if (accountNumber != null) {
                    predicates = cb.and(predicates, cb.equal(root.get("account").get("id").get("accountNumber"), accountNumber));
                }

                if (accountHolderName != null && !accountHolderName.isEmpty()) {
                    predicates = cb.and(predicates,
                            cb.like(cb.lower(root.get("account").get("name")), "%" + accountHolderName.toLowerCase() + "%"));
                }

                return predicates;
            });

            return listPixModel.stream()
                    .map(pixMapper::toDomain)
                    .toList();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long countPixByAccounts(List<Account> accounts) {
        List<AccountId> list = accountMapper.toAccountIdList(accounts);
        return pixJpaRepository.countPixByAccounts(list);
    }


    @Override
    public Optional<Pix> findById(String id) {
        Optional<PixModel> pixModel = pixJpaRepository.findById(id);

        if(pixModel.isEmpty()){
            return Optional.empty();
        }

        return Optional.of(pixMapper.toDomain(pixModel.get()));
    }

    @Override
    public Boolean existsByPixValue(String pixValue) {
        return pixJpaRepository.existsByPixValue(pixValue);
    }

    @Override
    public Pix updatePix(Account account, Pix pix) {

        AccountModel accountModel = accountJpaRepository.findById(new AccountId(
                account.getAccountNumber().getValue(),
                account.getAgencyNumber().getValue()
        )).orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));


        accountModel.setName(account.getName());
        accountModel.setLastName(account.getLastName());
        accountModel.setAccountType(account.getAccountType().name());


        accountJpaRepository.save(accountModel);


        PixModel pixModel = pixJpaRepository.findById(pix.getUniqueID().value())
                .orElseThrow(() -> new IllegalArgumentException("Pix não encontrado"));

        pixModel.setAccount(accountModel);

        pixJpaRepository.save(pixModel);

        return pixMapper.toDomain(pixModel);
    }


}
