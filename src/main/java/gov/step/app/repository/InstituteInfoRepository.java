package gov.step.app.repository;

import gov.step.app.domain.InstituteInfo;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the InstituteInfo entity.
 */
public interface InstituteInfoRepository extends JpaRepository<InstituteInfo,Long> {

}
