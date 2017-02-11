package gov.step.app.repository.search;

import gov.step.app.domain.PrincipalRequirement;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PrincipalRequirement entity.
 */
public interface PrincipalRequirementSearchRepository extends ElasticsearchRepository<PrincipalRequirement, Long> {
}
