package com.pix.poc.interactors.database.repository;

import com.pix.poc.interactors.database.model.AccountId;
import com.pix.poc.interactors.database.model.PixModel;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PixJpaRepository extends JpaRepository<PixModel, String>, JpaSpecificationExecutor<PixModel> {

   PixModel saveAndFlush(PixModel pixModel);

   Optional<PixModel> findById(String id);



   @Query("""
    SELECT COUNT(p.id) 
    FROM PixModel p
    WHERE p.account.id IN :accountIds
   """)
   Long countPixByAccounts(@Param("accountIds") List<AccountId> accountIds);

   boolean existsByPixValue(String pixValue);

}
