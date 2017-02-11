package gov.step.app.repository.search;

import gov.step.app.domain.DlBookRequisition;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the DlBookRequisition entity.
 */
public interface DlBookRequisitionSearchRepository extends ElasticsearchRepository<DlBookRequisition, Long> {
}
