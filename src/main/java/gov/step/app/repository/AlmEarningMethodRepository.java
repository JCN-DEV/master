package gov.step.app.repository;

import gov.step.app.domain.AlmEarningMethod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Spring Data JPA repository for the AlmEarningMethod entity.
 */
public interface AlmEarningMethodRepository extends JpaRepository<AlmEarningMethod,Long> {

    @Query("select modelInfo from AlmEarningMethod modelInfo where lower(modelInfo.earningMethodName) = :earningMethodName ")
    Optional<AlmEarningMethod> findOneByEarningMethodName(@Param("earningMethodName") String earningMethodName);

    Page<AlmEarningMethod> findAllByActiveStatus(Pageable pageable, boolean activeStatus);
}
