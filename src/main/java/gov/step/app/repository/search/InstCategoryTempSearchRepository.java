package gov.step.app.repository.search;

import gov.step.app.domain.InstCategoryTemp;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstCategoryTemp entity.
 */
public interface InstCategoryTempSearchRepository extends ElasticsearchRepository<InstCategoryTemp, Long> {
}
