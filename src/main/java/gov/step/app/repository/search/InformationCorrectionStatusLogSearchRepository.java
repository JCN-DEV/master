package gov.step.app.repository.search;

import gov.step.app.domain.InformationCorrectionStatusLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InformationCorrectionStatusLog entity.
 */
public interface InformationCorrectionStatusLogSearchRepository extends ElasticsearchRepository<InformationCorrectionStatusLog, Long> {
}
