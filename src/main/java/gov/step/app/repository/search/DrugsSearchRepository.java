package gov.step.app.repository.search;

import gov.step.app.domain.Drugs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Drugs entity.
 */
public interface DrugsSearchRepository extends ElasticsearchRepository<Drugs, Long> {
}
