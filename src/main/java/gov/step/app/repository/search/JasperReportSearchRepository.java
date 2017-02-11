package gov.step.app.repository.search;

import gov.step.app.domain.JasperReport;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the JasperReport entity.
 */
public interface JasperReportSearchRepository extends ElasticsearchRepository<JasperReport, Long> {
}
