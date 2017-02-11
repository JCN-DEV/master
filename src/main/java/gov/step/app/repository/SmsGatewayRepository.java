package gov.step.app.repository;

import gov.step.app.domain.SmsGateway;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SmsGateway entity.
 */
public interface SmsGatewayRepository extends JpaRepository<SmsGateway,Long> {

}
