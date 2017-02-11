package gov.step.app.repository;

import gov.step.app.domain.ProfessorApplicationEditLog;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProfessorApplicationEditLog entity.
 */
public interface ProfessorApplicationEditLogRepository extends JpaRepository<ProfessorApplicationEditLog,Long> {

}
