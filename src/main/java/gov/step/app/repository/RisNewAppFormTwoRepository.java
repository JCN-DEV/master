package gov.step.app.repository;

import gov.step.app.domain.RisNewAppForm;
import gov.step.app.domain.RisNewAppFormTwo;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the RisNewAppFormTwo entity.
 */
public interface RisNewAppFormTwoRepository extends JpaRepository<RisNewAppFormTwo,Long> {
    public Page<RisNewAppFormTwo> findAllByOrderByIdDesc(org.springframework.data.domain.Pageable pageable);
    @Query("select risNewAppFormTwo from RisNewAppFormTwo risNewAppFormTwo  where risNewAppFormTwo.risNewAppForm.id=:id ")
    RisNewAppFormTwo findRisAppDetailById( @Param("id") Long id);

}
