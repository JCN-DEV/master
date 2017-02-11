package gov.step.app.repository;

import gov.step.app.domain.InstFinancialInfo;
import gov.step.app.domain.InstFinancialInfoTemp;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the InstFinancialInfoTemp entity.
 */
public interface InstFinancialInfoTempRepository extends JpaRepository<InstFinancialInfoTemp,Long> {


    @Query("select instFinancialInfoTemp from InstFinancialInfoTemp instFinancialInfoTemp where instFinancialInfoTemp.institute.id =:instituteId")
    List<InstFinancialInfoTemp> findListByInstituteId(@Param("instituteId") Long instituteId);


    @Query("select instFinancialInfoTemp from InstFinancialInfoTemp instFinancialInfoTemp where instFinancialInfoTemp.institute.id =:instituteId")
    InstFinancialInfoTemp findOneByInstituteId(@Param("instituteId") Long instituteId);

    /*@Query("select instFinancialInfo from InstFinancialInfoTemp instFinancialInfoTemp where instFinancialInfoTemp.institute.id =:instituteId")
    InstFinancialInfoTemp findByInstituteId(@Param("instituteId") Long instituteId);*/
}
