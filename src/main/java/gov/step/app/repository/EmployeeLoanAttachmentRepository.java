package gov.step.app.repository;

import gov.step.app.domain.EmployeeLoanAttachment;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the EmployeeLoanAttachment entity.
 */
public interface EmployeeLoanAttachmentRepository extends JpaRepository<EmployeeLoanAttachment,Long> {

    @Query("select attachment from EmployeeLoanAttachment attachment where attachment.hrEmployeeInfo.id = :id")
    List<EmployeeLoanAttachment> findByEmployee(@Param("id") Long id);

    @Query("select attachment from EmployeeLoanAttachment attachment where attachment.hrEmployeeInfo.id = :id and attachment.attachmentCategory.applicationName = :applicationName")
    List<EmployeeLoanAttachment> findByEmployeeAndApplicationName(@Param("id") Long id, @Param("applicationName") String applicationName);

    @Query("select attachment from EmployeeLoanAttachment attachment where attachment.hrEmployeeInfo.id = :employeeInfoId and attachment.attachmentCategory.applicationName = :applicationName " +
                                                                    "and attachment.employeeLoanRequisitionForm.id = :requisitionId and attachment.status = true")
    List<EmployeeLoanAttachment> findByEmployeeAndAppNameAndRequisitionId(@Param("employeeInfoId") Long employeeInfoId, @Param("applicationName") String applicationName,@Param("requisitionId") Long requisitionId);

}
