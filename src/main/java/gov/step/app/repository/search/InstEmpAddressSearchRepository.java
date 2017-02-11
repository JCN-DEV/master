package gov.step.app.repository.search;

import gov.step.app.domain.InstEmpAddress;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstEmpAddress entity.
 */
public interface InstEmpAddressSearchRepository extends ElasticsearchRepository<InstEmpAddress, Long> {
}
