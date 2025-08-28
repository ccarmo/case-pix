package com.pix.poc.infra.database.repository;

import com.pix.poc.infra.database.model.AccountId;
import com.pix.poc.infra.database.model.AccountModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountJpaRepository extends JpaRepository<AccountModel, AccountId> {

    @Query("""
    SELECT a FROM AccountModel a
    WHERE a.documentNumber = :documentNumber
   """)
    List<AccountModel> findAllByDocument(
            @Param("documentNumber") String documentNumber
    );

    Optional<AccountModel> findByIdAccountNumberAndIdAgencyNumber(Integer accountNumber, Integer agencyNumber);


}
