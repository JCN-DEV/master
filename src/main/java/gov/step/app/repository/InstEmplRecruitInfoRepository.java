package gov.step.app.repository;

import gov.step.app.domain.InstEmpEduQuali;
import gov.step.app.domain.InstEmplRecruitInfo;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the InstEmplRecruitInfo entity.
 */
public interface InstEmplRecruitInfoRepository extends JpaRepository<InstEmplRecruitInfo,Long> {

    @Query("select instEmplRecruitInfo from InstEmplRecruitInfo instEmplRecruitInfo where instEmplRecruitInfo.instEmployee.id =:id")
    InstEmplRecruitInfo findByInstEmployeeId(@Param("id") Long id);

    @Query("select instEmplRecruitInfo from InstEmplRecruitInfo instEmplRecruitInfo where instEmplRecruitInfo.instEmployee.user.login =?#{principal.username}")
    InstEmplRecruitInfo findOneByCurrent();

}
