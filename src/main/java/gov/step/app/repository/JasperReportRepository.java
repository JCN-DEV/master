package gov.step.app.repository;

import gov.step.app.domain.JasperReport;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the JasperReport entity.
 */
public interface JasperReportRepository extends JpaRepository<JasperReport,Long> {

    //      Todo: Get all JasperReport by module name
    @Query("select jasperReport from JasperReport jasperReport where jasperReport.module = :module and status=1")
    List<JasperReport> findJasperReportByModule(@Param("module") String module);


}
