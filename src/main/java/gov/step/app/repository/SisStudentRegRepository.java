package gov.step.app.repository;

import gov.step.app.domain.SisStudentReg;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the SisStudentReg entity.
 */
public interface SisStudentRegRepository extends JpaRepository<SisStudentReg,Long> {

    @Query("select sisStudentReg from SisStudentReg sisStudentReg where sisStudentReg.applicationId = :applicationId")
    SisStudentReg findAllRegByAppId(@Param("applicationId") Long applicationId);
}
