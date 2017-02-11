package gov.step.app.repository;

import gov.step.app.domain.HrPayScaleSetup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Spring Data JPA repository for the HrPayScaleSetup entity.
 */
public interface HrPayScaleSetupRepository extends JpaRepository<HrPayScaleSetup,Long>
{
    @Query("select modelInfo from HrPayScaleSetup modelInfo where activeStatus =:activeStatus order by payScaleCode")
    Page<HrPayScaleSetup> findAllByActiveStatus(Pageable pageable, @Param("activeStatus") boolean activeStatus);

    @Query("select modelInfo from HrPayScaleSetup modelInfo where lower(modelInfo.payScaleCode) = :payScaleCode ")
    Optional<HrPayScaleSetup> findOneByPayScaleCode(@Param("payScaleCode") String payScaleCode);
}
