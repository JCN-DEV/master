package gov.step.app.repository;

import gov.step.app.domain.InsAcademicInfo;
import gov.step.app.domain.InstFinancialInfo;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the InstFinancialInfo entity.
 */
public interface InstFinancialInfoRepository extends JpaRepository<InstFinancialInfo,Long> {

    @Query("select instFinancialInfo from InstFinancialInfo instFinancialInfo where instFinancialInfo.institute.id =:instituteId")
    List<InstFinancialInfo> findListByInstituteId(@Param("instituteId") Long instituteId);

    @Query("select instFinancialInfo from InstFinancialInfo instFinancialInfo where instFinancialInfo.institute.id =:instituteId")
    InstFinancialInfo findByInstituteId(@Param("instituteId") Long instituteId);
}
