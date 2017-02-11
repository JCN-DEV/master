package gov.step.app.repository.search;

import gov.step.app.domain.TraineeInformation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TraineeInformation entity.
 */
public interface TraineeInformationSearchRepository extends ElasticsearchRepository<TraineeInformation, Long> {
}
