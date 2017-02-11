package gov.step.app.repository.search;

import gov.step.app.domain.InstEmplTraining;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstEmplTraining entity.
 */
public interface InstEmplTrainingSearchRepository extends ElasticsearchRepository<InstEmplTraining, Long> {
}
