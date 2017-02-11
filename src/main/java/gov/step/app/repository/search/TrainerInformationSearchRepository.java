package gov.step.app.repository.search;

import gov.step.app.domain.TrainerInformation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TrainerInformation entity.
 */
public interface TrainerInformationSearchRepository extends ElasticsearchRepository<TrainerInformation, Long> {
}
