package gov.step.app.repository;

import gov.step.app.domain.MpoCommitteeHistory;

import gov.step.app.domain.MpoCommitteePersonInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the MpoCommitteeHistory entity.
 */
public interface MpoCommitteeHistoryRepository extends JpaRepository<MpoCommitteeHistory,Long> {

    @Query("select mpoCommitteeHistory from MpoCommitteeHistory mpoCommitteeHistory where mpoCommitteeHistory.month = :month and mpoCommitteeHistory.year = :year")
    Page<MpoCommitteeHistory> findComitteeByMonthAndYear(@Param("month") Integer month, @Param("year") Integer year, Pageable pageable);

    @Query("select mpoCommitteeHistory from MpoCommitteeHistory mpoCommitteeHistory where mpoCommitteeHistory.month =:month and mpoCommitteeHistory.year =:year and mpoCommitteeHistory.mpoCommitteePersonInfo.user.email =:email")
    MpoCommitteeHistory findComitteeMemberByEmailAndMonthAndYear(@Param("month") Integer month, @Param("year") Integer year, @Param("email") String email);

    Page<MpoCommitteeHistory> findByMonthAndYear(Integer month, Integer year, Pageable pageable);
}
