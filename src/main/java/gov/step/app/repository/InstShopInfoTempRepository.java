package gov.step.app.repository;

import gov.step.app.domain.InstShopInfo;
import gov.step.app.domain.InstShopInfoTemp;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the InstShopInfoTemp entity.
 */
public interface InstShopInfoTempRepository extends JpaRepository<InstShopInfoTemp,Long> {

    @Query("select instShopInfo from InstShopInfoTemp instShopInfo where instShopInfo.instInfraInfoTemp.institute.id =:instituteId")
    List<InstShopInfoTemp> findListByInstituteId(@Param("instituteId") Long instituteId);
}
