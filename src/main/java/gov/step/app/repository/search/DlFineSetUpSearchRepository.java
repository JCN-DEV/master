package gov.step.app.repository.search;

import gov.step.app.domain.DlFineSetUp;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the DlFineSetUp entity.
 */
public interface DlFineSetUpSearchRepository extends ElasticsearchRepository<DlFineSetUp, Long> {
}
