package com.pix.poc.interactors.database.repository;

import com.pix.poc.domain.entities.Account;
import com.pix.poc.domain.entities.Pix;

import com.pix.poc.domain.repository.PixRepository;

import com.pix.poc.interactors.database.mapper.AccountMapper;
import com.pix.poc.interactors.database.mapper.PixMapper;
import com.pix.poc.interactors.database.model.AccountId;
import com.pix.poc.interactors.database.model.PixModel;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class PixDatabaseRepository implements PixRepository {

    PixJpaRepository pixJpaRepository;
    AccountMapper accountMapper;
    PixMapper pixMapper;

    public PixDatabaseRepository(PixJpaRepository pixJpaRepository, PixMapper pixMapper, AccountMapper accountMapper) {
        this.pixJpaRepository = pixJpaRepository;
        this.accountMapper = accountMapper;
        this.pixMapper = pixMapper;
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

            List<PixModel> listPixModel = pixJpaRepository.findByPixDynamicFilter(
                    pixType,
                    agencyNumber,
                    accountNumber,
                    name,
                    inclusionDate,
                    inactivationDate
            );

            return listPixModel.stream()
                    .map(pixModel -> pixMapper.toDomain(pixModel))
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


}
