package com.pix.poc.interactors.database.repository;

import com.pix.poc.interactors.database.model.AccountModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountJpaRepository extends JpaRepository<AccountModel, String> {

    @Query("""
    SELECT a FROM AccountModel a
    WHERE a.documentNumber = :documentNumber
   """)
    List<AccountModel> findAllByDocument(
            @Param("documentNumber") String documentNumber
    );
}
