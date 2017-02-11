package gov.step.app.repository.search;

import gov.step.app.domain.AlmEarningMethod;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the AlmEarningMethod entity.
 */
public interface AlmEarningMethodSearchRepository extends ElasticsearchRepository<AlmEarningMethod, Long> {
}
