package gov.step.app.repository;

import gov.step.app.domain.HrEmpAddressInfo;
import gov.step.app.domain.enumeration.addressTypes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the HrEmpAddressInfo entity.
 */
public interface HrEmpAddressInfoRepository extends JpaRepository<HrEmpAddressInfo,Long>
{
    @Query("select modelInfo from HrEmpAddressInfo modelInfo where modelInfo.employeeInfo.user.login = ?#{principal.username} AND modelInfo.employeeInfo.activeAccount=true ")
    Page<HrEmpAddressInfo> findAllByEmployeeIsCurrentUser(Pageable pageable);

    List<HrEmpAddressInfo> findAllByLogStatusAndActiveStatus(Long logStatus, boolean activeStatus);

    @Query("select modelInfo from HrEmpAddressInfo modelInfo where logStatus=:logStatus order by updateDate asc")
    List<HrEmpAddressInfo> findAllByLogStatus(@Param("logStatus") Long logStatus);

    // Added By Bappi Mazumder
    @Query("select addressInfo from HrEmpAddressInfo addressInfo where addressInfo.employeeInfo.id = :pEmployeeInfoId and addressInfo.addressType = :pAddressType")
    HrEmpAddressInfo findByEmployeeIdAndType(@Param("pEmployeeInfoId") Long pEmployeeInfoId,@Param("pAddressType") addressTypes pAddressType);

}
