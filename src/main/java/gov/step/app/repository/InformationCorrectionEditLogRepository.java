package gov.step.app.repository;

import gov.step.app.domain.InformationCorrectionEditLog;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the InformationCorrectionEditLog entity.
 */
public interface InformationCorrectionEditLogRepository extends JpaRepository<InformationCorrectionEditLog,Long> {

}
