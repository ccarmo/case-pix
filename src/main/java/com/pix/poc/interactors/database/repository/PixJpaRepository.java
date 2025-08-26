package com.pix.poc.interactors.database.repository;

import com.pix.poc.interactors.database.model.AccountId;
import com.pix.poc.interactors.database.model.PixModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PixJpaRepository extends JpaRepository<PixModel, String> {

   PixModel saveAndFlush(PixModel pixModel);

   Optional<PixModel> findById(String id);

   @Query("""
    SELECT p FROM PixModel p
    WHERE (:pixType IS NULL OR p.pixType = :pixType)
      AND (:inclusionDate IS NULL OR p.inclusionDate = :inclusionDate)
      AND (:inactivationDate IS NULL OR p.inactivationDate = :inactivationDate)
      AND (:accountHolderName IS NULL OR (p.account.name = :accountHolderName OR p.account.lastName = :accountHolderName))
      AND (:accountNumber IS NULL OR p.account.id.accountNumber = :accountNumber)
      AND (:agencyNumber IS NULL OR p.account.id.agencyNumber = :agencyNumber)
   """)
   List<PixModel> findByPixDynamicFilter(
           @Param("pixType") String pixType,
           @Param("agencyNumber") Integer agencyNumber,
           @Param("accountNumber") Integer accountNumber,
           @Param("accountHolderName") String accountHolderName,
           @Param("inclusionDate") LocalDate inclusionDate,
           @Param("inactivationDate") LocalDate inactivationDate
   );

   @Query("""
    SELECT COUNT(p.id) 
    FROM PixModel p
    WHERE p.account.id IN :accountIds
   """)
   Long countPixByAccounts(@Param("accountIds") List<AccountId> accountIds);
}
