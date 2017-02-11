package gov.step.app.repository.search;

import gov.step.app.domain.PfmsUtmostGpfApp;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PfmsUtmostGpfApp entity.
 */
public interface PfmsUtmostGpfAppSearchRepository extends ElasticsearchRepository<PfmsUtmostGpfApp, Long> {
}
