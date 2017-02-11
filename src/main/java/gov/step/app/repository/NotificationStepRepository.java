package gov.step.app.repository;

import gov.step.app.domain.NotificationStep;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the NotificationStep entity.
 */
public interface NotificationStepRepository extends JpaRepository<NotificationStep,Long> {

    @Query("select notificationStep from NotificationStep notificationStep where notificationStep.userId = :userId AND notificationStep.status = :status")
    Page<NotificationStep> findAllnotificationByuserId(Pageable pageable, @Param("userId") Long userId, @Param("status") Boolean status);

}
