package gov.step.app.repository;

import gov.step.app.domain.AssetRecord;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the AssetRecord entity.
 */
public interface AssetRecordRepository extends JpaRepository<AssetRecord,Long> {

    @Query("select assetRecord from AssetRecord assetRecord where assetRecord.assetTypeSetup.id = :assetTypeSetupId and assetRecord.assetCategorySetup.id = :assetCategorySetupId")
    List<AssetRecord> findAllByTypeIdAndCategoryId(@Param("assetTypeSetupId") Long assetTypeSetupId, @Param("assetCategorySetupId") Long assetCategorySetupId);

}
