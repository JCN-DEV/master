package gov.step.app.repository;

import gov.step.app.domain.SmsServiceDepartment;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SmsServiceDepartment entity.
 */
public interface SmsServiceDepartmentRepository extends JpaRepository<SmsServiceDepartment,Long> {

    @Query("select smsServiceDepartment from SmsServiceDepartment smsServiceDepartment where smsServiceDepartment.user.login = ?#{principal.username}")
    List<SmsServiceDepartment> findByUserIsCurrentUser();

}
