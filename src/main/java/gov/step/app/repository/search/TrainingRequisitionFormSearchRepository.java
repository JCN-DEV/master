package gov.step.app.repository.search;

import gov.step.app.domain.TrainingRequisitionForm;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TrainingRequisitionForm entity.
 */
public interface TrainingRequisitionFormSearchRepository extends ElasticsearchRepository<TrainingRequisitionForm, Long> {
}
