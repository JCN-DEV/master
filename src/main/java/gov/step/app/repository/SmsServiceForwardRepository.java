package gov.step.app.repository;

import gov.step.app.domain.SmsServiceForward;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the SmsServiceForward entity.
 */
public interface SmsServiceForwardRepository extends JpaRepository<SmsServiceForward,Long> {

    @Query("select smsServiceForward from SmsServiceForward smsServiceForward where smsServiceForward.forwarder.login = ?#{principal.username}")
    List<SmsServiceForward> findByForwarderIsCurrentUser();

    @Query("select smsServiceForward from SmsServiceForward smsServiceForward where smsServiceForward.smsServiceComplaint.id = :id")
    List<SmsServiceForward> findByComplaint(@Param("id") Long id);
}
