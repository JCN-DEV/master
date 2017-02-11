package gov.step.app.repository;

import gov.step.app.domain.HrProjectInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the HrProjectInfo entity.
 */
public interface HrProjectInfoRepository extends JpaRepository<HrProjectInfo,Long> {

}
