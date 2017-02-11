package gov.step.app.repository;

import gov.step.app.domain.InstEmplDesignation;
import gov.step.app.domain.InstVacancy;

import gov.step.app.domain.enumeration.designationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the InstVacancy entity.
 */
public interface InstVacancyRepository extends JpaRepository<InstVacancy,Long> {

    @Query("select instVacancy from InstVacancy instVacancy where instVacancy.institute.id = :instId and instVacancy.cmsTrade.id = :tradeId and instVacancy.cmsSubject.id = :subjectId")
    InstVacancy findOneByInstituteAndTradeAndSubject(@Param("instId") Long instId, @Param("tradeId") Long tradeId,@Param("subjectId") Long subjectId);

    @Query("select instVacancy from InstVacancy instVacancy where instVacancy.institute.id = :instId and instVacancy.cmsSubject.id = :subjectId")
    InstVacancy findOneByInstituteAndSubject(@Param("instId") Long instId ,@Param("subjectId") Long subjectId);

   @Query("select instVacancy from InstVacancy instVacancy where instVacancy.institute.id = :instId")
    List<InstVacancy> findAllByInstitute(@Param("instId") Long instId);

    @Query("select instVacancy from InstVacancy instVacancy where instVacancy.institute.id = :instId and instVacancy.designationSetup.id = :desgId")
    InstVacancy findOneByDesignation(@Param("instId") Long instId, @Param("desgId") Long desgId);

}
