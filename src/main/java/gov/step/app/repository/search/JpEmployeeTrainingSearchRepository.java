package gov.step.app.repository.search;

import gov.step.app.domain.JpEmployeeTraining;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the JpEmployeeTraining entity.
 */
public interface JpEmployeeTrainingSearchRepository extends ElasticsearchRepository<JpEmployeeTraining, Long> {
}
