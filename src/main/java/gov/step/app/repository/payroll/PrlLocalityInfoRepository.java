package gov.step.app.repository.payroll;

import gov.step.app.domain.payroll.PrlLocalityInfo;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the PrlLocalityInfo entity.
 */
public interface PrlLocalityInfoRepository extends JpaRepository<PrlLocalityInfo,Long>
{
    @Query("select modelInfo from PrlLocalityInfo modelInfo where modelInfo.district.id =:distid AND lower(modelInfo.name) = lower(:name) ")
    Optional<PrlLocalityInfo> findOneByDistrictAndName(@Param("distid") Long distid, @Param("name") String name);

}
