package gov.step.app.repository.search;

import gov.step.app.domain.JpEmploymentHistory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the JpEmploymentHistory entity.
 */
public interface JpEmploymentHistorySearchRepository extends ElasticsearchRepository<JpEmploymentHistory, Long> {
}
