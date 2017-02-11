package gov.step.app.repository;

import gov.step.app.domain.EmployeeLoanTypeSetup;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the EmployeeLoanTypeSetup entity.
 */
public interface EmployeeLoanTypeSetupRepository extends JpaRepository<EmployeeLoanTypeSetup,Long> {

    @Query("select employeeLoanTypeSetup from EmployeeLoanTypeSetup employeeLoanTypeSetup " +
                    "where employeeLoanTypeSetup.loanTypeCode = :loanTypeCode")
    Optional<EmployeeLoanTypeSetup> findOneByLoanTypeCode(@Param("loanTypeCode") String loanTypeCode);

    @Query("select employeeLoanTypeSetup from EmployeeLoanTypeSetup employeeLoanTypeSetup where employeeLoanTypeSetup.loanTypeName = :loanTypeName")
    Optional<EmployeeLoanTypeSetup> findOneByLoanTypeName(@Param("loanTypeName") String loanTypeName);

    @Query("select employeeLoanTypeSetup from EmployeeLoanTypeSetup employeeLoanTypeSetup where employeeLoanTypeSetup.status = :activeStatus")
    Page<EmployeeLoanTypeSetup> findByActiveStatus(Pageable pageable,@Param("activeStatus") boolean activeStatus);

}
