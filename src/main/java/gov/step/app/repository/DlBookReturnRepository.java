package gov.step.app.repository;

import gov.step.app.domain.DlBookIssue;
import gov.step.app.domain.DlBookReturn;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the DlBookReturn entity.
 */
public interface DlBookReturnRepository extends JpaRepository<DlBookReturn,Long> {
    @Query("select dlBookReturn from DlBookReturn dlBookReturn where dlBookReturn.institute.user.login = ?#{principal.username}")
    List<DlBookReturn> findAllBookReturnByUserIsCurrentUser();

    @Query("select dlBookReturn from DlBookReturn dlBookReturn where dlBookReturn.dlBookIssue.id = :id")
   DlBookReturn findReturnInfoByStuid(@Param("id") Long id);


}
