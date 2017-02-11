package gov.step.app.repository.search;

import gov.step.app.domain.InformationCorrection;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InformationCorrection entity.
 */
public interface InformationCorrectionSearchRepository extends ElasticsearchRepository<InformationCorrection, Long> {
}
