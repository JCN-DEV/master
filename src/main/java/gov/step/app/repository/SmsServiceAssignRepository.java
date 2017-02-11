package gov.step.app.repository;

import gov.step.app.domain.SmsServiceAssign;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the SmsServiceAssign entity.
 */
public interface SmsServiceAssignRepository extends JpaRepository<SmsServiceAssign,Long> {

    @Query("select smsServiceAssign from SmsServiceAssign smsServiceAssign where smsServiceAssign.user.login = ?#{principal.username}")
    List<SmsServiceAssign> findByUserIsCurrentUser();

    @Query("select smsServiceAssign from SmsServiceAssign smsServiceAssign where smsServiceAssign.employee.id =:id")
    List<SmsServiceAssign> findListByEmployee(@Param("id") Long id);

    @Query("select smsServiceAssign from SmsServiceAssign smsServiceAssign where smsServiceAssign.employee.user.login = ?#{principal.username}")
    List<SmsServiceAssign> findListByEmployeeUser();

}
