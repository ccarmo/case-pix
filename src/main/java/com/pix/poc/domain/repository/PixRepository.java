package com.pix.poc.domain.repository;

import com.pix.poc.domain.entities.Account;
import com.pix.poc.domain.entities.Pix;

import com.pix.poc.domain.vo.Document;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface PixRepository {

    UUID save(Pix pix);
    List<Pix> get(String id,
                  String pixType,
                  String uniqueId,
                  Integer agencyNumber,
                  Integer accountNumber,
                  String name,
                  LocalDate inclusionDate,
                  LocalDate inactivationDate
    );

    Long countPixByAccounts(List<Account> accounts);



}
