package com.pix.poc.domain.repository;

import com.pix.poc.domain.entities.Account;
import com.pix.poc.domain.entities.Pix;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface PixRepository {

    Pix save(Pix pix);
    List<Pix> get(String id,
                  String pixType,
                  String uniqueId,
                  Integer agencyNumber,
                  Integer accountNumber,
                  String name,
                  LocalDate inclusionDate,
                  LocalDate inactivationDate
    );

    List<Pix> findByDynamicFilter(
            String pixType,
            Integer agencyNumber,
            Integer accountNumber,
            String accountHolderName,
            LocalDate inclusionDate,
            LocalDate inactivationDate
    );
    Long countPixByAccounts(List<Account> accounts);
    Optional<Pix> findById(String id);
    Boolean existsByPixValue(String pixValue);





}
