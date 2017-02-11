package gov.step.app.repository.search;

import gov.step.app.domain.DlContTypeSet;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the DlContTypeSet entity.
 */
public interface DlContTypeSetSearchRepository extends ElasticsearchRepository<DlContTypeSet, Long> {
}
