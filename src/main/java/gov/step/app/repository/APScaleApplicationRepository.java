package gov.step.app.repository;

import gov.step.app.domain.APScaleApplication;

import gov.step.app.domain.TimeScaleApplication;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the APScaleApplication entity.
 */
public interface APScaleApplicationRepository extends JpaRepository<APScaleApplication,Long> {


    @Query("select aPScaleApplication from APScaleApplication aPScaleApplication where aPScaleApplication.instEmployee.code = :code")
    APScaleApplication findByInstEmployeeCode(@org.springframework.data.repository.query.Param("code") String code);

}
