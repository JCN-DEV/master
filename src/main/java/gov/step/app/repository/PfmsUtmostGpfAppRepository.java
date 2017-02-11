package gov.step.app.repository;

import gov.step.app.domain.PfmsUtmostGpfApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the PfmsUtmostGpfApp entity.
 */
public interface PfmsUtmostGpfAppRepository extends JpaRepository<PfmsUtmostGpfApp,Long> {
    @Query("select modelInfo from PfmsUtmostGpfApp modelInfo where modelInfo.employeeInfo.id = :employeeInfoId")
    List<PfmsUtmostGpfApp> getPfmsUtmostGpfAppListByEmployee(@Param("employeeInfoId") long employeeInfoId);
}
