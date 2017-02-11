package gov.step.app.repository;

import gov.step.app.domain.DlSourceSetUp;

import org.springframework.data.domain.Page;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the DlSourceSetUp entity.
 */
public interface DlSourceSetUpRepository extends JpaRepository<DlSourceSetUp,Long> {

    @Query("select dlSourceSetUp from DlSourceSetUp dlSourceSetUp where dlSourceSetUp.status = 1 ")
    Page<DlSourceSetUp> activeSourceTypes(org.springframework.data.domain.Pageable pageable);


}
