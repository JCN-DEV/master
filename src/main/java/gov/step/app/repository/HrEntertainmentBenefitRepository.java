package gov.step.app.repository;

import gov.step.app.domain.HrEntertainmentBenefit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the HrEntertainmentBenefit entity.
 */
public interface HrEntertainmentBenefitRepository extends JpaRepository<HrEntertainmentBenefit,Long>
{
    @Query("select modelInfo from HrEntertainmentBenefit modelInfo where modelInfo.employeeInfo.user.login = ?#{principal.username} AND modelInfo.employeeInfo.activeAccount=true")
    List<HrEntertainmentBenefit> findAllByEmployeeIsCurrentUser();

    List<HrEntertainmentBenefit> findAllByLogStatusAndActiveStatus(Long logStatus, boolean activeStatus);

    @Query("select modelInfo from HrEntertainmentBenefit modelInfo where logStatus=:logStatus order by updateDate asc")
    List<HrEntertainmentBenefit> findAllByLogStatus(@Param("logStatus") Long logStatus);
}
