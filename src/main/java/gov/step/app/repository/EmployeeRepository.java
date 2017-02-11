package gov.step.app.repository;

import gov.step.app.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Employee entity.
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("select employee from Employee employee where employee.manager.login = ?#{principal.username}")
    List<Employee> findByManagerIsCurrentUser();

    @Query("select distinct employee from Employee employee left join fetch employee.payScales")
    List<Employee> findAllWithEagerRelationships();

    @Query("select employee from Employee employee left join fetch employee.payScales where employee.id =:id")
    Employee findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select employee from Employee employee where employee.user.login = ?#{principal.username}")
    Employee findOneByEmployeeUserIsCurrentUser();

    @Query("select employee from Employee employee where employee.institute.id = :id")
    List<Employee> findByEmployee(@Param("id") Long id);


}
