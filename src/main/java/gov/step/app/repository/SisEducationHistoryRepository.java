package gov.step.app.repository;

import gov.step.app.domain.SisEducationHistory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the SisEducationHistory entity.
 */
public interface SisEducationHistoryRepository extends JpaRepository<SisEducationHistory,Long> {

    @Query("select sisEducationHistory from SisEducationHistory sisEducationHistory where sisEducationHistory.createBy = :createBy")
    Page<SisEducationHistory> findStudentsByUserId(Pageable pageable, @Param("createBy") Long createBy);
}
