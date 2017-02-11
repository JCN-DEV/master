package gov.step.app.repository;

import gov.step.app.domain.AssetAuctionInformation;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AssetAuctionInformation entity.
 */
public interface AssetAuctionInformationRepository extends JpaRepository<AssetAuctionInformation,Long> {

}
