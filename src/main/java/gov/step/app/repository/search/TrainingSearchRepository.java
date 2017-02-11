package gov.step.app.repository.search;

import gov.step.app.domain.Training;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the Training entity.
 */
public interface TrainingSearchRepository extends ElasticsearchRepository<Training, Long> {
}
