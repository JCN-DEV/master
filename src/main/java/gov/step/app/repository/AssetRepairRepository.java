package gov.step.app.repository;

import gov.step.app.domain.AssetRepair;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the AssetRepair entity.
 */
public interface AssetRepairRepository extends JpaRepository<AssetRepair,Long> {

    @Query("select repair from AssetRecord repair where repair.id = :assetCode")
    AssetRepair findAllRecordCodeassetCode(@Param("assetCode") Long assetCode);

}
