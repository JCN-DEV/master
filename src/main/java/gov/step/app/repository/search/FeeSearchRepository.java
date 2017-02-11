package gov.step.app.repository.search;

import gov.step.app.domain.Fee;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Fee entity.
 */
public interface FeeSearchRepository extends ElasticsearchRepository<Fee, Long> {
}
