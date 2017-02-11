package gov.step.app.repository.search;

import gov.step.app.domain.VclRequisition;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the VclRequisition entity.
 */
public interface VclRequisitionSearchRepository extends ElasticsearchRepository<VclRequisition, Long> {
}
