package gov.step.app.repository;

import gov.step.app.domain.TempInstGenInfo;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TempInstGenInfo entity.
 */
public interface TempInstGenInfoRepository extends JpaRepository<TempInstGenInfo,Long> {

}
