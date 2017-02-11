package gov.step.app.repository;

import gov.step.app.domain.CmsSemester;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the CmsSemester entity.
 */
public interface CmsSemesterRepository extends JpaRepository<CmsSemester,Long> {
    @Query("select cmsSemester from CmsSemester cmsSemester where cmsSemester.code = :code AND cmsSemester.name = :name")
   CmsSemester findOneByCodeAndName(@Param("code") String code, @Param("name") String name);

}

