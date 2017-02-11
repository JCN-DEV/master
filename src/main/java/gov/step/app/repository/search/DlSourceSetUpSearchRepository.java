package gov.step.app.repository.search;

import gov.step.app.domain.DlSourceSetUp;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the DlSourceSetUp entity.
 */
public interface DlSourceSetUpSearchRepository extends ElasticsearchRepository<DlSourceSetUp, Long> {
}
