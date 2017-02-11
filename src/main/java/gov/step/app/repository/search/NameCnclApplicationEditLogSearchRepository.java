package gov.step.app.repository.search;

import gov.step.app.domain.NameCnclApplicationEditLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the NameCnclApplicationEditLog entity.
 */
public interface NameCnclApplicationEditLogSearchRepository extends ElasticsearchRepository<NameCnclApplicationEditLog, Long> {
}
