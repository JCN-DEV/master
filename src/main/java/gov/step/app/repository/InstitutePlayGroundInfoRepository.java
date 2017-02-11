package gov.step.app.repository;

import gov.step.app.domain.InstitutePlayGroundInfo;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the InstitutePlayGroundInfo entity.
 */
public interface InstitutePlayGroundInfoRepository extends JpaRepository<InstitutePlayGroundInfo,Long> {

}
