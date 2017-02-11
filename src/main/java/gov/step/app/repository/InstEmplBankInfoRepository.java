package gov.step.app.repository;

import gov.step.app.domain.InstEmplBankInfo;

import gov.step.app.domain.InstEmplRecruitInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the InstEmplBankInfo entity.
 */
public interface InstEmplBankInfoRepository extends JpaRepository<InstEmplBankInfo,Long> {

    @Query("select instEmplBankInfo from InstEmplBankInfo instEmplBankInfo where instEmplBankInfo.instEmployee.id =:id")
    InstEmplBankInfo findByInstEmployeeId(@Param("id") Long id);
}
