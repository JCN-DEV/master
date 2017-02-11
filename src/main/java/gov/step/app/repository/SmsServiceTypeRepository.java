package gov.step.app.repository;

import gov.step.app.domain.SmsServiceType;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the SmsServiceType entity.
 */
public interface SmsServiceTypeRepository extends JpaRepository<SmsServiceType,Long>
{
    @Query("select smsServiceType from SmsServiceType smsServiceType where smsServiceType.activeStatus = :stat")
    List<SmsServiceType> findAllByActiveStatus(@Param("stat") boolean stat);

}
