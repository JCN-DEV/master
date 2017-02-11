package gov.step.app.repository;

import gov.step.app.domain.NameCnclApplicationEditLog;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the NameCnclApplicationEditLog entity.
 */
public interface NameCnclApplicationEditLogRepository extends JpaRepository<NameCnclApplicationEditLog,Long> {

}
