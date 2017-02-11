package gov.step.app.repository;

import gov.step.app.domain.IisCurriculumInfo;
import gov.step.app.domain.InstCategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the InstCategory entity.
 */
public interface InstCategoryRepository extends JpaRepository<InstCategory,Long> {

    @Query("select instCategory from InstCategory instCategory where instCategory.pStatus = :val")
    Page<InstCategory> findAllInstCategoriesByType(@Param("val") Boolean status, Pageable pageable);

    @Query("select instCategory from InstCategory instCategory where instCategory.code = :code")
    InstCategory instUniqueCategory(@Param("code") String code);

    @Query("select instCategory from InstCategory instCategory where instCategory.name = :name")
    InstCategory instCategoryUniqueName(@Param("name") String name);

}
