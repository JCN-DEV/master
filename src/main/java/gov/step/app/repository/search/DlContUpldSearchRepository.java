package gov.step.app.repository.search;

import gov.step.app.domain.DlContUpld;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the DlContUpld entity.
 */
public interface DlContUpldSearchRepository extends ElasticsearchRepository<DlContUpld, Long> {
}
