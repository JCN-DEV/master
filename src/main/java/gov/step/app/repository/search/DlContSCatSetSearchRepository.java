package gov.step.app.repository.search;

import gov.step.app.domain.DlContSCatSet;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the DlContSCatSet entity.
 */
public interface DlContSCatSetSearchRepository extends ElasticsearchRepository<DlContSCatSet, Long> {
}
