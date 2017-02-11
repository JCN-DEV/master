package gov.step.app.repository;

import gov.step.app.domain.BankBranch;

import gov.step.app.domain.Upazila;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the BankBranch entity.
 */
public interface BankBranchRepository extends JpaRepository<BankBranch,Long> {


    @Query("select bankBranch from BankBranch bankBranch where bankBranch.upazila.id = :id and bankBranch.bankSetup.id = :bankSetupId order by bankBranch.brName")
    Page<BankBranch> findBankBranchByUpazilaAndBankOrderByName(Pageable pageable, @Param("id") Long id, @Param("bankSetupId") Long bankSetupId);

    @Query("select bankBranch from BankBranch bankBranch where bankBranch.upazila.district.id = :districtId and bankBranch.bankSetup.id = :bankSetupId order by bankBranch.brName")
    Page<BankBranch> findBankBranchByDistrictAndBankOrderByName(Pageable pageable, @Param("districtId") Long districtId, @Param("bankSetupId") Long bankSetupId);


}
