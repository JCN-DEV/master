package gov.step.app.repository;

import gov.step.app.domain.PfmsEmpMonthlyAdj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the PfmsEmpMonthlyAdj entity.
 */
public interface PfmsEmpMonthlyAdjRepository extends JpaRepository<PfmsEmpMonthlyAdj,Long> {
//    @Query("select modelInfo from PfmsEmpMonthlyAdj modelInfo where modelInfo.paymentDate = (select MAX(modelInfoa.paymentDate) FROM PfmsEmpMonthlyAdj modelInfoa WHERE modelInfoa.employeeInfo.id = modelInfoa.entityId)")
//    List<PfmsEmpMonthlyAdj> findByMaxDate(@Param("employeeId") HrEmployeeInfo employeeId);

@Query("select modelInfo from PfmsEmpMonthlyAdj modelInfo where modelInfo.employeeInfo.id = :employeeInfoId and modelInfo.activeStatus = true")
List<PfmsEmpMonthlyAdj> getPfmsEmpMonthlyAdjListByEmployee(@Param("employeeInfoId") long employeeInfoId);
}
