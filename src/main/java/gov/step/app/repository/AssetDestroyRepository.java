package gov.step.app.repository;

import gov.step.app.domain.AssetDestroy;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AssetDestroy entity.
 */
public interface AssetDestroyRepository extends JpaRepository<AssetDestroy,Long> {

}
