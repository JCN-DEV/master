package gov.step.app.repository;

import gov.step.app.domain.AssetDistribution;

import gov.step.app.domain.AssetTypeSetup;
import gov.step.app.domain.HrEmployeeInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the AssetDistribution entity.
 */
public interface AssetDistributionRepository extends JpaRepository<AssetDistribution,Long> {

}
