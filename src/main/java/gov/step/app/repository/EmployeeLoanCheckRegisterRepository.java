package gov.step.app.repository;

import gov.step.app.domain.EmployeeLoanCheckRegister;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the EmployeeLoanCheckRegister entity.
 */
public interface EmployeeLoanCheckRegisterRepository extends JpaRepository<EmployeeLoanCheckRegister,Long> {

    @Query("select loanCheckRegister from EmployeeLoanCheckRegister loanCheckRegister where loanCheckRegister.checkNumber = :checkNumber and loanCheckRegister.status = 1")
    EmployeeLoanCheckRegister findByCheckNumber(@Param("checkNumber") String checkNumber);

}
