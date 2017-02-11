package gov.step.app.repository;

import gov.step.app.domain.InstLabInfo;
import gov.step.app.domain.InstLabInfoTemp;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the InstLabInfoTemp entity.
 */
public interface InstLabInfoTempRepository extends JpaRepository<InstLabInfoTemp,Long> {

    @Query("select instLabInfo from InstLabInfoTemp instLabInfo where instLabInfo.instInfraInfoTemp.institute.id =:instituteId")
    List<InstLabInfoTemp> findListByInstituteId(@Param("instituteId") Long instituteId);
}
