package gov.step.app.repository.search;

import gov.step.app.domain.TempEmployer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TempEmployer entity.
 */
public interface TempEmployerSearchRepository extends ElasticsearchRepository<TempEmployer, Long> {
}
