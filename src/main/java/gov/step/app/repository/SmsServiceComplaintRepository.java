package gov.step.app.repository;

import gov.step.app.domain.SmsServiceComplaint;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Spring Data JPA repository for the SmsServiceComplaint entity.
 */
public interface SmsServiceComplaintRepository extends JpaRepository<SmsServiceComplaint,Long>
{
    @Query("select smsServiceComplaint from SmsServiceComplaint smsServiceComplaint where smsServiceComplaint.smsServiceDepartment.id = :id AND smsServiceComplaint.activeStatus=true")
    List<SmsServiceComplaint> findByDepartment(@Param("id") Long id);

    Page<SmsServiceComplaint> findByActiveStatus(boolean activeStatus, Pageable pageable);

    @Modifying
    @Transactional
    @Query("update SmsServiceComplaint smsServiceComplaint set smsServiceComplaint.activeStatus=false where smsServiceComplaint.id = :id")
    void updateComplaintByActiveStatus(@Param("id") Long id);

}
