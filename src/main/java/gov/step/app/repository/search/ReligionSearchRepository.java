package gov.step.app.repository.search;

import gov.step.app.domain.Religion;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Religion entity.
 */
public interface ReligionSearchRepository extends ElasticsearchRepository<Religion, Long> {
}
