package gov.step.app.repository.search;

import gov.step.app.domain.BEdApplicationEditLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the BEdApplicationEditLog entity.
 */
public interface BEdApplicationEditLogSearchRepository extends ElasticsearchRepository<BEdApplicationEditLog, Long> {
}
