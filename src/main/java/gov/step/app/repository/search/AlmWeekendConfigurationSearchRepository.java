package gov.step.app.repository.search;

import gov.step.app.domain.AlmWeekendConfiguration;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the AlmWeekendConfiguration entity.
 */
public interface AlmWeekendConfigurationSearchRepository extends ElasticsearchRepository<AlmWeekendConfiguration, Long> {
}
