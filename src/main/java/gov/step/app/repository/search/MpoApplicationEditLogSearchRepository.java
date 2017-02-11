package gov.step.app.repository.search;

import gov.step.app.domain.MpoApplicationEditLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the MpoApplicationEditLog entity.
 */
public interface MpoApplicationEditLogSearchRepository extends ElasticsearchRepository<MpoApplicationEditLog, Long> {
}
