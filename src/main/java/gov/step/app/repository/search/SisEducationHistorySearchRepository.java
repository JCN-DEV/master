package gov.step.app.repository.search;

import gov.step.app.domain.SisEducationHistory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the SisEducationHistory entity.
 */
public interface SisEducationHistorySearchRepository extends ElasticsearchRepository<SisEducationHistory, Long> {
}
