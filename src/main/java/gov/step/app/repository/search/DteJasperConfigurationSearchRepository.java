package gov.step.app.repository.search;

import gov.step.app.domain.DteJasperConfiguration;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the DteJasperConfiguration entity.
 */
public interface DteJasperConfigurationSearchRepository extends ElasticsearchRepository<DteJasperConfiguration, Long> {
}
