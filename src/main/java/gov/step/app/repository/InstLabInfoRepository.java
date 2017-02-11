package gov.step.app.repository;

import gov.step.app.domain.InstLabInfo;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the InstLabInfo entity.
 */
public interface InstLabInfoRepository extends JpaRepository<InstLabInfo,Long> {

    @Query("select instLabInfo from InstLabInfo instLabInfo where instLabInfo.instInfraInfo.institute.id =:instituteId")
    List<InstLabInfo> findListByInstituteId(@Param("instituteId") Long instituteId);

}
