package gov.step.app.repository.search;

import gov.step.app.domain.AlmEarningFrequency;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the AlmEarningFrequency entity.
 */
public interface AlmEarningFrequencySearchRepository extends ElasticsearchRepository<AlmEarningFrequency, Long> {
}
