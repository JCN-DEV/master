package gov.step.app.repository;

import gov.step.app.domain.AlmDutySide;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Spring Data JPA repository for the AlmDutySide entity.
 */
public interface AlmDutySideRepository extends JpaRepository<AlmDutySide,Long> {

    @Query("select modelInfo from AlmDutySide modelInfo where lower(modelInfo.sideName) = :sideName ")
    Optional<AlmDutySide> findOneBySideName(@Param("sideName") String sideName);

    Page<AlmDutySide> findAllByActiveStatus(Pageable pageable, boolean activeStatus);
}
