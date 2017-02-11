package gov.step.app.repository;

import gov.step.app.domain.InformationCorrection;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the InformationCorrection entity.
 */
public interface InformationCorrectionRepository extends JpaRepository<InformationCorrection,Long> {

    @Query("select informationCorrection from InformationCorrection informationCorrection where informationCorrection.instEmployee.code = :code")
    InformationCorrection findByInstEmployeeCode(@Param("code") String code);
}
