package gov.step.app.repository;

import gov.step.app.domain.JasperReportParameter;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the JasperReportParameter entity.
 */
public interface JasperReportParameterRepository extends JpaRepository<JasperReportParameter,Long> {

    @Query("select jasperReportParameter from JasperReportParameter jasperReportParameter where jasperReportParameter.jasperReport.id = :id order by jasperReportParameter.position")
    List<JasperReportParameter> findByJasperReport(@Param("id") Long id);


}
