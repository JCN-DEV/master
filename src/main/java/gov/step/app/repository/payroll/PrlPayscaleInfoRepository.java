package gov.step.app.repository.payroll;

import gov.step.app.domain.payroll.PrlPayscaleInfo;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the PrlPayscaleInfo entity.
 */
public interface PrlPayscaleInfoRepository extends JpaRepository<PrlPayscaleInfo,Long>
{
    @Query("select modelInfo from PrlPayscaleInfo modelInfo where gazetteInfo.id = :gztid AND gradeInfo.id =:grdid ")
    PrlPayscaleInfo findByGazzeteAndGradeInfo(@Param("gztid") long gztid, @Param("grdid") long grdid);

    @Query("select modelInfo from PrlPayscaleInfo modelInfo where gazetteInfo.id = :gztid AND gradeInfo.id =:grdid AND id != :psid ")
    PrlPayscaleInfo findByGazzeteAndGradeExceptSelf(@Param("gztid") long gztid, @Param("grdid") long grdid, @Param("psid") long psid);
}
