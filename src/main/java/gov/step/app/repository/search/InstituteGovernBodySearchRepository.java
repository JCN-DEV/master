package gov.step.app.repository.search;

import gov.step.app.domain.InstituteGovernBody;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstituteGovernBody entity.
 */
public interface InstituteGovernBodySearchRepository extends ElasticsearchRepository<InstituteGovernBody, Long> {
}
