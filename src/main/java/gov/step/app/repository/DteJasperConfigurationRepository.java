package gov.step.app.repository;

import gov.step.app.domain.DteJasperConfiguration;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the DteJasperConfiguration entity.
 */
public interface DteJasperConfigurationRepository extends JpaRepository<DteJasperConfiguration,Long> {

    @Query("select dteJasperConfiguration from DteJasperConfiguration dteJasperConfiguration where dteJasperConfiguration.user.login = ?#{principal.username}")
    List<DteJasperConfiguration> findByUserIsCurrentUser();

}
