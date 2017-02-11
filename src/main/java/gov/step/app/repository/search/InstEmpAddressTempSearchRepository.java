package gov.step.app.repository.search;

import gov.step.app.domain.InstEmpAddressTemp;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstEmpAddressTemp entity.
 */
public interface InstEmpAddressTempSearchRepository extends ElasticsearchRepository<InstEmpAddressTemp, Long> {
}
