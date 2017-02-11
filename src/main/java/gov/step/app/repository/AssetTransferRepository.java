package gov.step.app.repository;

import gov.step.app.domain.AssetTransfer;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AssetTransfer entity.
 */
public interface AssetTransferRepository extends JpaRepository<AssetTransfer,Long> {

}
