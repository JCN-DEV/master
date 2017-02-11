package gov.step.app.repository;

import gov.step.app.domain.DlBookInfo;
import gov.step.app.domain.Institute;
import gov.step.app.domain.SisStudentInfo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the SisStudentInfo entity.
 */
public interface SisStudentInfoRepository extends JpaRepository<SisStudentInfo,Long> {

    @Query("select sisStudentInfo from SisStudentInfo sisStudentInfo where sisStudentInfo.emailAddress = :emailAddress")
    Page<SisStudentInfo> findStudentsByEmailId(Pageable pageable, @Param("emailAddress") String emailAddress);

    @Query("select sisStudentInfo from SisStudentInfo sisStudentInfo where sisStudentInfo.id =:id AND sisStudentInfo.institute.id=:instId")
    SisStudentInfo findStudentInfoById(@Param("id") Long id,@Param("instId") Long instId);

    @Query("select sisStudentInfo from SisStudentInfo sisStudentInfo where sisStudentInfo.user.login = ?#{principal.username}")
    SisStudentInfo findOneByUserIsCurrentUser();

    @Query("select sisStudentInfo from SisStudentInfo sisStudentInfo where sisStudentInfo.id =:id AND sisStudentInfo.institute.id=:instId")
    SisStudentInfo findStudentInfoByInstituteWise(@Param("id") Long id,@Param("instId") Long instId);


}
