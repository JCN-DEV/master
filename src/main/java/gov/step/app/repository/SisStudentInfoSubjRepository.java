package gov.step.app.repository;

import gov.step.app.domain.SisStudentInfoSubj;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SisStudentInfoSubj entity.
 */
public interface SisStudentInfoSubjRepository extends JpaRepository<SisStudentInfoSubj,Long> {

}
