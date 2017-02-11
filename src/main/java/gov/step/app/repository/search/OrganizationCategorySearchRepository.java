package gov.step.app.repository.search;

import gov.step.app.domain.OrganizationCategory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the OrganizationCategory entity.
 */
public interface OrganizationCategorySearchRepository extends ElasticsearchRepository<OrganizationCategory, Long> {
}
