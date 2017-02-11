package gov.step.app.repository;

import gov.step.app.domain.AssetAccuisitionSetup;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the AssetAccuisitionSetup entity.
 */
public interface AssetAccuisitionSetupRepository extends JpaRepository<AssetAccuisitionSetup,Long> {

    @Query("select assetAccuisitionSetup from AssetAccuisitionSetup assetAccuisitionSetup where assetAccuisitionSetup.code = :code")
    Optional<AssetAccuisitionSetup> findOneByCode(@Param("code") String code);

    @Query("select assetAccuisitionSetup from AssetAccuisitionSetup assetAccuisitionSetup where assetAccuisitionSetup.name = :name")
    Optional<AssetAccuisitionSetup> findOneByName(@Param("name") String name);

}
