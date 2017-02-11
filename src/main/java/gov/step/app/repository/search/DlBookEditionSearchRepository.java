package gov.step.app.repository.search;

import gov.step.app.domain.DlBookEdition;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the DlBookEdition entity.
 */
public interface DlBookEditionSearchRepository extends ElasticsearchRepository<DlBookEdition, Long> {
}
