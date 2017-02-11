package gov.step.app.repository.search;

import gov.step.app.domain.Module;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Module entity.
 */
public interface ModuleSearchRepository extends ElasticsearchRepository<Module, Long> {
}
