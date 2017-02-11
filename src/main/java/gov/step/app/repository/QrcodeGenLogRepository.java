package gov.step.app.repository;

import gov.step.app.domain.QrcodeGenLog;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the QrcodeGenLog entity.
 */
public interface QrcodeGenLogRepository extends JpaRepository<QrcodeGenLog,Long> {

}
