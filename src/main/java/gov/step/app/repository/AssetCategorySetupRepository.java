package gov.step.app.repository;

import gov.step.app.domain.AssetCategorySetup;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the AssetCategorySetup entity.
 */
public interface AssetCategorySetupRepository extends JpaRepository<AssetCategorySetup,Long> {

    @Query("select assetCategorySetup from AssetCategorySetup assetCategorySetup where assetCategorySetup.categoryName = :categoryName")
    Optional<AssetCategorySetup> findOneByName(@Param("categoryName") String categoryName);

    @Query("select assetCategorySetup from AssetCategorySetup assetCategorySetup where assetCategorySetup.categoryCode = :categoryCode")
    Optional<AssetCategorySetup> findOneByCode(@Param("categoryCode") String categoryCode);

    @Query("select assetCategorySetup from AssetCategorySetup assetCategorySetup where assetCategorySetup.assetTypeSetup.id = :assetTypeSetupId")
    List<AssetCategorySetup> findAllByTypeId(@Param("assetTypeSetupId") Long assetTypeSetupId);

}
