package gov.step.app.repository;

import gov.step.app.domain.StaffCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Spring Data JPA repository for the StaffCount entity.
 */
public interface StaffCountRepository extends JpaRepository<StaffCount, Long> {

    @Query("select staffCount from StaffCount staffCount where staffCount.manager.login = ?#{principal.username}")
    List<StaffCount> findByManagerIsCurrentUser();

}
