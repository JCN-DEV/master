package gov.step.app.repository.search;

import gov.step.app.domain.PayScale;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PayScale entity.
 */
public interface PayScaleSearchRepository extends ElasticsearchRepository<PayScale, Long> {
}
