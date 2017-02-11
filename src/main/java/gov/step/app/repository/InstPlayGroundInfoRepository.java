package gov.step.app.repository;

import gov.step.app.domain.InstPlayGroundInfo;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the InstPlayGroundInfo entity.
 */
public interface InstPlayGroundInfoRepository extends JpaRepository<InstPlayGroundInfo,Long> {

    @Query("select instPlayGroundInfo from InstPlayGroundInfo instPlayGroundInfo where instPlayGroundInfo.instInfraInfo.institute.id =:instituteId")
    List<InstPlayGroundInfo> findListByInstituteId(@Param("instituteId") Long instituteId);


}
