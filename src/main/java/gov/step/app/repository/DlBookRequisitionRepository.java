package gov.step.app.repository;

import gov.step.app.domain.DlBookRequisition;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the DlBookRequisition entity.
 */
public interface DlBookRequisitionRepository extends JpaRepository<DlBookRequisition,Long> {

    @Query("select dlBookRequisition from DlBookRequisition dlBookRequisition where dlBookRequisition.status=true AND dlBookRequisition.sisStudentInfo.institute.user.login = ?#{principal.username} ")
    Page<DlBookRequisition> findAllbyBookUser(Pageable pageable);

}
