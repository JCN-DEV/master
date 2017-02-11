package gov.step.app.repository;

import gov.step.app.domain.BEdApplication;

import gov.step.app.domain.MpoApplication;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the BEdApplication entity.
 */
public interface BEdApplicationRepository extends JpaRepository<BEdApplication,Long> {

    @Query("select bEdApplication from BEdApplication bEdApplication where bEdApplication.instEmployee.code = :code")
    BEdApplication findByInstEmployeeCode(@Param("code") String code);
}
