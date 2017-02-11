package gov.step.app.repository;

import gov.step.app.domain.RisAppFormEduQ;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the RisAppFormEduQ entity.
 */
public interface RisAppFormEduQRepository extends JpaRepository<RisAppFormEduQ,Long> {
    @Query("select risAppFormEduQ from RisAppFormEduQ risAppFormEduQ  where risAppFormEduQ.risNewAppForm.id=:id ")
    List<RisAppFormEduQ> getAllEducationByAppId(@Param("id") Long id);

}
