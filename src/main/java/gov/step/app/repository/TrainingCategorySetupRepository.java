package gov.step.app.repository;

import gov.step.app.domain.TrainingCategorySetup;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the TrainingCategorySetup entity.
 */
public interface TrainingCategorySetupRepository extends JpaRepository<TrainingCategorySetup,Long> {

    public Page<TrainingCategorySetup> findAllByOrderByIdDesc(Pageable pageable);

    @Query("select modelInfo from TrainingCategorySetup modelInfo where modelInfo.categoryName = :categoryName")
    Optional<TrainingCategorySetup> findByCategoryName(@Param("categoryName") String categoryName );

    public Page<TrainingCategorySetup> findAllByStatus(Boolean status,Pageable pageable);



}
