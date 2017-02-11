package gov.step.app.repository;

import gov.step.app.domain.AuditLog;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AuditLog entity.
 */
public interface AuditLogRepository extends JpaRepository<AuditLog,Long> {

}
