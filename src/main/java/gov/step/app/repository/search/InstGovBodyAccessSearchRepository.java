package gov.step.app.repository.search;

import gov.step.app.domain.InstGovBodyAccess;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstGovBodyAccess entity.
 */
public interface InstGovBodyAccessSearchRepository extends ElasticsearchRepository<InstGovBodyAccess, Long> {
}
