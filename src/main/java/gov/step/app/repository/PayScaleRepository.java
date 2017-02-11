package gov.step.app.repository;

import gov.step.app.domain.PayScale;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PayScale entity.
 */
public interface PayScaleRepository extends JpaRepository<PayScale,Long> {

    @Query("select payScale from PayScale payScale where payScale.manager.login = ?#{principal.username}")
    List<PayScale> findByManagerIsCurrentUser();

    //TODO: change query when GazetteSetup linked up to PayScale. temporarily using payScaleClass
    @Query("select payScale from PayScale payScale where payScale.payScaleClass = 'Ninth Pay Scale' order by payScale.grade * 1")
    Page<PayScale> findAllByActiveGazette(Pageable pageable);

}
