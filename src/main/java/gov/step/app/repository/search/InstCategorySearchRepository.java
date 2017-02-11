package gov.step.app.repository.search;

import gov.step.app.domain.InstCategory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstCategory entity.
 */
public interface InstCategorySearchRepository extends ElasticsearchRepository<InstCategory, Long> {
}
