package gov.step.app.repository.search;

import gov.step.app.domain.TrainingRequisitionApproveAndForward;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TrainingRequisitionApproveAndForward entity.
 */
public interface TrainingRequisitionApproveAndForwardSearchRepository extends ElasticsearchRepository<TrainingRequisitionApproveAndForward, Long> {
}
