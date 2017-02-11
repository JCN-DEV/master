package gov.step.app.repository;

import gov.step.app.domain.AlmEarningFrequency;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Spring Data JPA repository for the AlmEarningFrequency entity.
 */
public interface AlmEarningFrequencyRepository extends JpaRepository<AlmEarningFrequency,Long> {

    @Query("select modelInfo from AlmEarningFrequency modelInfo where lower(modelInfo.earningFrequencyName) = :earningFrequencyName ")
    Optional<AlmEarningFrequency> findOneByEarningFrequencyName(@Param("earningFrequencyName") String earningFrequencyName);

    Page<AlmEarningFrequency> findAllByActiveStatus(Pageable pageable, boolean activeStatus);
}

