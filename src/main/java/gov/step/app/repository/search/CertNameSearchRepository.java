package gov.step.app.repository.search;

import gov.step.app.domain.CertName;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the CertName entity.
 */
public interface CertNameSearchRepository extends ElasticsearchRepository<CertName, Long> {
}
