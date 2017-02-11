package gov.step.app.repository;

import gov.step.app.domain.Employee;
import gov.step.app.domain.JpEmployee;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the JpEmployee entity.
 */
public interface JpEmployeeRepository extends JpaRepository<JpEmployee,Long> {

    @Query("select jpEmployee from JpEmployee jpEmployee where jpEmployee.user.login = ?#{principal.username}")
    JpEmployee findOneByEmployeeUserIsCurrentUser();

}
