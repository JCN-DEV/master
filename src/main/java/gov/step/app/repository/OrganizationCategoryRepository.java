package gov.step.app.repository;

import gov.step.app.domain.Country;
import gov.step.app.domain.OrganizationCategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the OrganizationCategory entity.
 */
public interface OrganizationCategoryRepository extends JpaRepository<OrganizationCategory,Long> {

    public Page<OrganizationCategory> findAllByOrderByIdDesc(Pageable pageable);

    @Query("SELECT organizationCategory from OrganizationCategory organizationCategory where lower(organizationCategory.name) = :name")
    OrganizationCategory findOneByName(@Param("name") String name);

    @Query("SELECT organizationCategory from OrganizationCategory organizationCategory where organizationCategory.status = true")
    Page<OrganizationCategory> findAllActiveOrgCat(Pageable pageable);

}
