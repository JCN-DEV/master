package gov.step.app.repository;

import gov.step.app.domain.InstAcaInfo;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the InstAcaInfo entity.
 */
public interface InstAcaInfoRepository extends JpaRepository<InstAcaInfo,Long> {

}
