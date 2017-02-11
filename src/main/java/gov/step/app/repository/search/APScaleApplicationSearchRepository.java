package gov.step.app.repository.search;

import gov.step.app.domain.APScaleApplication;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the APScaleApplication entity.
 */
public interface APScaleApplicationSearchRepository extends ElasticsearchRepository<APScaleApplication, Long> {
}
