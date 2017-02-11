package gov.step.app.repository.search;

import gov.step.app.domain.SisQuota;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the SisQuota entity.
 */
public interface SisQuotaSearchRepository extends ElasticsearchRepository<SisQuota, Long> {
}
