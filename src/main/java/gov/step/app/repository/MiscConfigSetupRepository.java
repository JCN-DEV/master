package gov.step.app.repository;

import gov.step.app.domain.MiscConfigSetup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the MiscConfigSetup entity.
 */
public interface MiscConfigSetupRepository extends JpaRepository<MiscConfigSetup,Long>
{
    @Query("select modelInfo from MiscConfigSetup modelInfo where activeStatus =:activeStatus order by propertyName asc")
    Page<MiscConfigSetup> findAllByActiveStatus(@Param("activeStatus") boolean activeStatus, Pageable pageable);

    @Query("select modelInfo from MiscConfigSetup modelInfo where modelInfo.propertyName = :propertyName ")
    MiscConfigSetup findOneByPropertyName(@Param("propertyName") String propertyName);


}
