package gov.step.app.repository;

import gov.step.app.domain.OrganizationCategory;
import gov.step.app.domain.OrganizationType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the OrganizationType entity.
 */
public interface OrganizationTypeRepository extends JpaRepository<OrganizationType,Long> {

    public Page<OrganizationType> findAllByOrderByIdDesc(Pageable pageable);

    @Query("SELECT organizationType from OrganizationType organizationType where lower(organizationType.name) = :name")
    OrganizationType findOneByName(@Param("name") String name);

    @Query("SELECT organizationType from OrganizationType organizationType where organizationType.status = true ")
    Page<OrganizationType> findAllActiveOrgType(Pageable pageable);

}
