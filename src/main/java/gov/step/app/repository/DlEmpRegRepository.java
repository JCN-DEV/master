package gov.step.app.repository;

import gov.step.app.domain.DlEmpReg;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the DlEmpReg entity.
 */
public interface DlEmpRegRepository extends JpaRepository<DlEmpReg,Long> {

}
