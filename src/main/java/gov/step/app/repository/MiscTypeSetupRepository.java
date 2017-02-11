package gov.step.app.repository;

import gov.step.app.domain.MiscTypeSetup;

import gov.step.app.domain.enumeration.miscTypeCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the MiscTypeSetup entity.
 */
public interface MiscTypeSetupRepository extends JpaRepository<MiscTypeSetup,Long>
{
    @Query("select modelInfo from MiscTypeSetup modelInfo where typeCategory=:typeCategory order by typeName asc")
    Page<MiscTypeSetup> findByTypeCategory(@Param("typeCategory") String typeCategory, Pageable pageable);

    @Query("select modelInfo from MiscTypeSetup modelInfo where activeStatus =:activeStatus AND typeCategory=:typeCategory order by typeName asc")
    Page<MiscTypeSetup> findByTypeCategoryAndActiveStatus(@Param("typeCategory") String typeCategory,@Param("activeStatus") boolean activeStatus, Pageable pageable);

    @Query("select modelInfo from MiscTypeSetup modelInfo where lower(modelInfo.typeCategory) = lower(:category) AND lower(modelInfo.typeName) = lower(:name) ")
    List<MiscTypeSetup> findOneByCategoryAndName(@Param("category") String category, @Param("name") String name);

    @Query("select modelInfo from MiscTypeSetup modelInfo where lower(modelInfo.typeCategory) = lower(:category) AND lower(modelInfo.typeTitle) = lower(:title) ")
    List<MiscTypeSetup> findOneByCategoryAndTitle(@Param("category") String category, @Param("title") String title);
}
