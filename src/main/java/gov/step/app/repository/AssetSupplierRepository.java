package gov.step.app.repository;

import gov.step.app.domain.AssetSupplier;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AssetSupplier entity.
 */
public interface AssetSupplierRepository extends JpaRepository<AssetSupplier,Long> {

}
