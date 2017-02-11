package gov.step.app.repository;

import gov.step.app.domain.InstEmpAddress;

import gov.step.app.domain.InstEmployee;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the InstEmpAddress entity.
 */
public interface InstEmpAddressRepository extends JpaRepository<InstEmpAddress,Long> {

    @Query("select instEmpAddress from InstEmpAddress instEmpAddress where instEmpAddress.instEmployee.id =:id")
    InstEmpAddress findOneByInstEmployeeId(@Param("id") Long id);

}
