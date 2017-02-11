package gov.step.app.repository.search;

import gov.step.app.domain.ExternalCV;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ExternalCV entity.
 */
public interface ExternalCVSearchRepository extends ElasticsearchRepository<ExternalCV, Long> {
}
