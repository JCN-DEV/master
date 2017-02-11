package gov.step.app.repository.search;

import gov.step.app.domain.JasperReportParameter;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the JasperReportParameter entity.
 */
public interface JasperReportParameterSearchRepository extends ElasticsearchRepository<JasperReportParameter, Long> {
}
