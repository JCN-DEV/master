package gov.step.app.repository.search;

import gov.step.app.domain.BoardName;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the BoardName entity.
 */
public interface BoardNameSearchRepository extends ElasticsearchRepository<BoardName, Long> {
}
