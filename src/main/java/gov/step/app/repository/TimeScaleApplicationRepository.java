package gov.step.app.repository;

import gov.step.app.domain.TimeScaleApplication;

import org.jboss.logging.annotations.Param;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TimeScaleApplication entity.
 */
public interface TimeScaleApplicationRepository extends JpaRepository<TimeScaleApplication,Long> {

    @Query("select timeScaleApplication from TimeScaleApplication timeScaleApplication where timeScaleApplication.instEmployee.code = :code")
    TimeScaleApplication findByInstEmployeeCode(@org.springframework.data.repository.query.Param("code") String code);
}
