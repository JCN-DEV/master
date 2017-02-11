package gov.step.app.repository;

import gov.step.app.domain.VclEmployeeAssign;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the VclEmployeeAssign entity.
 */
public interface VclEmployeeAssignRepository extends JpaRepository<VclEmployeeAssign,Long> {

    @Query("select distinct vclEmployeeAssign from VclEmployeeAssign vclEmployeeAssign left join fetch vclEmployeeAssign.employees")
    List<VclEmployeeAssign> findAllWithEagerRelationships();

    @Query("select vclEmployeeAssign from VclEmployeeAssign vclEmployeeAssign left join fetch vclEmployeeAssign.employees where vclEmployeeAssign.id =:id")
    VclEmployeeAssign findOneWithEagerRelationships(@Param("id") Long id);

}
