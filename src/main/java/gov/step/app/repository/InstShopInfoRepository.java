package gov.step.app.repository;

import gov.step.app.domain.InstShopInfo;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the InstShopInfo entity.
 */
public interface InstShopInfoRepository extends JpaRepository<InstShopInfo,Long> {

    @Query("select instShopInfo from InstShopInfo instShopInfo where instShopInfo.instInfraInfo.institute.id =:instituteId")
    List<InstShopInfo> findListByInstituteId(@Param("instituteId") Long instituteId);

}
