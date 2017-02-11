package gov.step.app.repository.search;

import gov.step.app.domain.Result;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Result entity.
 */
public interface ResultSearchRepository extends ElasticsearchRepository<Result, Long> {
}
