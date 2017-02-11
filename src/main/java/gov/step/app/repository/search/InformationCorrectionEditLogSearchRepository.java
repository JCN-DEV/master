package gov.step.app.repository.search;

import gov.step.app.domain.InformationCorrectionEditLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InformationCorrectionEditLog entity.
 */
public interface InformationCorrectionEditLogSearchRepository extends ElasticsearchRepository<InformationCorrectionEditLog, Long> {
}
