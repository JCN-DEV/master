package gov.step.app.repository;

import gov.step.app.domain.InstPlayGroundInfo;
import gov.step.app.domain.InstPlayGroundInfoTemp;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the InstPlayGroundInfoTemp entity.
 */
public interface InstPlayGroundInfoTempRepository extends JpaRepository<InstPlayGroundInfoTemp,Long> {

    @Query("select instPlayGroundInfo from InstPlayGroundInfoTemp instPlayGroundInfo where instPlayGroundInfo.instInfraInfoTemp.institute.id =:instituteId")
    List<InstPlayGroundInfoTemp> findListByInstituteId(@Param("instituteId") Long instituteId);
}
