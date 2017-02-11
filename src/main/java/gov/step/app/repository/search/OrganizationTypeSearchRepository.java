package gov.step.app.repository.search;

import gov.step.app.domain.OrganizationType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the OrganizationType entity.
 */
public interface OrganizationTypeSearchRepository extends ElasticsearchRepository<OrganizationType, Long> {
}
