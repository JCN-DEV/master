package gov.step.app.repository;

import gov.step.app.domain.PgmsNotification;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PgmsNotification entity.
 */
public interface PgmsNotificationRepository extends JpaRepository<PgmsNotification,Long> {

}
