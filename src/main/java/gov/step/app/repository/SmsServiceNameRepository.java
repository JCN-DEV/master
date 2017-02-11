package gov.step.app.repository;

import gov.step.app.domain.SmsServiceName;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SmsServiceName entity.
 */
public interface SmsServiceNameRepository extends JpaRepository<SmsServiceName,Long> {

}
