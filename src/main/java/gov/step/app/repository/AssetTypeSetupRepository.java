package gov.step.app.repository;

import gov.step.app.domain.AssetCategorySetup;
import gov.step.app.domain.AssetTypeSetup;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the AssetTypeSetup entity.
 */
public interface AssetTypeSetupRepository extends JpaRepository<AssetTypeSetup,Long> {

    @Query("select assetTypeSetup from AssetTypeSetup assetTypeSetup where assetTypeSetup.typeCode = :code")
    AssetTypeSetup findOneByTypeCode(@Param("code") String code);


    @Query("select assetTypeSetup from AssetTypeSetup assetTypeSetup where assetTypeSetup.typeCode = :typeCode")
    Optional<AssetTypeSetup> findAllByTypeCode(@Param("typeCode") String typeCode);


    @Query("select assetTypeSetup from AssetTypeSetup assetTypeSetup where assetTypeSetup.typeName = :typeName")
    Optional<AssetTypeSetup> findAllByTypeName(@Param("typeName")String typeName);

}
