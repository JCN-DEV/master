package gov.step.app.repository;

import gov.step.app.domain.HrGradeSetup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the HrGradeSetup entity.
 */
public interface HrGradeSetupRepository extends JpaRepository<HrGradeSetup,Long>
{
    @Query("select modelInfo from HrGradeSetup modelInfo where activeStatus =:activeStatus order by gradeCode")
    Page<HrGradeSetup> findAllByActiveStatus(Pageable pageable, @Param("activeStatus") boolean activeStatus);

    @Query("select modelInfo from HrGradeSetup modelInfo where activeStatus =:activeStatus order by gradeCode")
    List<HrGradeSetup> findAllByActiveStatus(@Param("activeStatus") boolean activeStatus);

    @Query("select modelInfo from HrGradeSetup modelInfo where lower(modelInfo.gradeCode) = :gradeCode ")
    Optional<HrGradeSetup> findOneByGradeCode(@Param("gradeCode") String gradeCode);
}
