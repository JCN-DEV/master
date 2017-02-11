package gov.step.app.repository;

import gov.step.app.domain.HrSpouseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the HrSpouseInfo entity.
 */
public interface HrSpouseInfoRepository extends JpaRepository<HrSpouseInfo,Long>
{
    @Query("select modelInfo from HrSpouseInfo modelInfo where modelInfo.employeeInfo.user.login = ?#{principal.username} AND modelInfo.employeeInfo.activeAccount=true")
    List<HrSpouseInfo> findAllByEmployeeIsCurrentUser();

    List<HrSpouseInfo> findAllByLogStatusAndActiveStatus(Long logStatus, boolean activeStatus);

    @Query("select modelInfo from HrSpouseInfo modelInfo where logStatus=:logStatus order by updateDate asc")
    List<HrSpouseInfo> findAllByLogStatus(@Param("logStatus") Long logStatus);

    @Query("select modelInfo from HrSpouseInfo modelInfo where lower(modelInfo.nationalId) = :nationalId ")
    Optional<HrSpouseInfo> findOneByNationalId(@Param("nationalId") String nationalId);
}
