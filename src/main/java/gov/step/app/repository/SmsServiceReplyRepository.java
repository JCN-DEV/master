package gov.step.app.repository;

import gov.step.app.domain.SmsServiceReply;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the SmsServiceReply entity.
 */
public interface SmsServiceReplyRepository extends JpaRepository<SmsServiceReply,Long> {

    @Query("select smsServiceReply from SmsServiceReply smsServiceReply where smsServiceReply.replier.login = ?#{principal.username}")
    List<SmsServiceReply> findByReplierIsCurrentUser();

    @Query("select smsServiceReply from SmsServiceReply smsServiceReply where smsServiceReply.smsServiceComplaint.id = :id")
    List<SmsServiceReply> findByComplaint(@Param("id") Long id);

    @Query("select smsServiceReply from SmsServiceReply smsServiceReply where smsServiceReply.smsServiceComplaint.smsServiceDepartment.id = :id")
    List<SmsServiceReply> findByDepartment(@Param("id") Long id);
}
