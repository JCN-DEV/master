package gov.step.app.repository;

import gov.step.app.domain.InstituteLabInfo;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the InstituteLabInfo entity.
 */
public interface InstituteLabInfoRepository extends JpaRepository<InstituteLabInfo,Long> {

}
