package gov.step.app.repository.search;

import gov.step.app.domain.PfmsRegister;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PfmsRegister entity.
 */
public interface PfmsRegisterSearchRepository extends ElasticsearchRepository<PfmsRegister, Long> {
}
