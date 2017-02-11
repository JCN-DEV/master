package gov.step.app.repository.payroll;

import gov.step.app.domain.payroll.PrlPayscaleBasicInfo;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the PrlPayscaleBasicInfo entity.
 */
public interface PrlPayscaleBasicInfoRepository extends JpaRepository<PrlPayscaleBasicInfo,Long>
{
    @Query("select modelInfo from PrlPayscaleBasicInfo modelInfo where payscaleInfo.id=:psid order by serialNumber asc")
    List<PrlPayscaleBasicInfo> findAllByPayScaleId(@Param("psid") Long psid);
}
