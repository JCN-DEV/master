package gov.step.app.repository;

import gov.step.app.domain.HrEmpWorkAreaDtlInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the HrEmpWorkAreaDtlInfo entity.
 */
public interface HrEmpWorkAreaDtlInfoRepository extends JpaRepository<HrEmpWorkAreaDtlInfo,Long>
{
    @Query("select modelInfo from HrEmpWorkAreaDtlInfo modelInfo where activeStatus =:activeStatus")
    List<HrEmpWorkAreaDtlInfo> findAllByActiveStatus(@Param("activeStatus") boolean activeStatus);

}
