package gov.step.app.repository;

import gov.step.app.domain.InstEmpSpouseInfo;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the InstEmpSpouseInfo entity.
 */
public interface InstEmpSpouseInfoRepository extends JpaRepository<InstEmpSpouseInfo,Long> {


    @Query("select instEmpSpouseInfo from InstEmpSpouseInfo instEmpSpouseInfo where instEmpSpouseInfo.instEmployee.user.login =?#{principal.username}")
    InstEmpSpouseInfo findOneByLogin();

    @Query("select instEmpSpouseInfo from InstEmpSpouseInfo instEmpSpouseInfo where instEmpSpouseInfo.instEmployee.id =:id")
    InstEmpSpouseInfo findByInstEmployeeId(@Param("id") Long id);

}
