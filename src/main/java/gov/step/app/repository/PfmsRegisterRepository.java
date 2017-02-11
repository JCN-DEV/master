package gov.step.app.repository;

import gov.step.app.domain.PfmsRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the PfmsRegister entity.
 */
public interface PfmsRegisterRepository extends JpaRepository<PfmsRegister,Long> {
    @Query("select modelInfo from PfmsRegister modelInfo where modelInfo.employeeInfo.id = :employeeInfoId and modelInfo.activeStatus = true")
    List<PfmsRegister> getPfmsRegisterListByEmployee(@Param("employeeInfoId") long employeeInfoId);

}
