package gov.step.app.repository;


import gov.step.app.domain.MpoSalaryFlow;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the MpoSalaryFlow entity.
 */
public interface MpoSalaryFlowRepository extends JpaRepository<MpoSalaryFlow,Long> {

    @Query("select mpoSalaryFlow from MpoSalaryFlow mpoSalaryFlow where mpoSalaryFlow.forwardToRole = :forwardToRole")
    Page<MpoSalaryFlow> findAllRequestByRole(Pageable pageable, @Param("forwardToRole") String forwardToRole);

}
