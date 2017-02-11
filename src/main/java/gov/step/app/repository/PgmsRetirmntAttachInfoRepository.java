package gov.step.app.repository;

import gov.step.app.domain.PgmsRetirmntAttachInfo;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PgmsRetirmntAttachInfo entity.
 */
public interface PgmsRetirmntAttachInfoRepository extends JpaRepository<PgmsRetirmntAttachInfo,Long>
{
    List<PgmsRetirmntAttachInfo> findAllByAttachType(String attachType );
}
