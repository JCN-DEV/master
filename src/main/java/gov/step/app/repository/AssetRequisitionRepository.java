package gov.step.app.repository;

import gov.step.app.domain.AssetRequisition;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the AssetRequisition entity.
 */
public interface AssetRequisitionRepository extends JpaRepository<AssetRequisition,Long> {

    @Query("select assetRequisition from AssetRequisition assetRequisition where assetRequisition.status = :status")
    Page<AssetRequisition> findRequisitionByStatus(Pageable pageable, @Param("status") Boolean status);

    @Query("select assetRequisition from AssetRequisition assetRequisition where assetRequisition.createBy = :createBy")
    Page<AssetRequisition> findRequisitionByUserId(Pageable pageable, @Param("createBy") Long createBy);

    @Query("select assetRequisition from AssetRequisition assetRequisition where assetRequisition.id = :id")
    AssetRequisition findRequisitionByEmployeeId(@Param("id") Long id);
}
