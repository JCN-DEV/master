package gov.step.app.repository;

import gov.step.app.domain.MpoCommitteePersonInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Spring Data JPA repository for the MpoCommitteePersonInfo entity.
 */
public interface MpoCommitteePersonInfoRepository extends JpaRepository<MpoCommitteePersonInfo,Long> {

    @Query("select mpoCommitteePersonInfo from MpoCommitteePersonInfo mpoCommitteePersonInfo where mpoCommitteePersonInfo.createdBy.login = ?#{principal.username}")
    List<MpoCommitteePersonInfo> findByCreatedByIsCurrentUser();

    @Query("select mpoCommitteePersonInfo from MpoCommitteePersonInfo mpoCommitteePersonInfo where mpoCommitteePersonInfo.user.email = :email")
    MpoCommitteePersonInfo findPersonByEmail(@Param("email") String email);


    Page<MpoCommitteePersonInfo> findByActivated(Boolean active,Pageable pageable);

    public Page<MpoCommitteePersonInfo> findAllByOrderByIdDesc(Pageable pageable);

}
