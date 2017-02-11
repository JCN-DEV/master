package gov.step.app.repository.search;

import gov.step.app.domain.Quota;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Quota entity.
 */
public interface QuotaSearchRepository extends ElasticsearchRepository<Quota, Long> {
}