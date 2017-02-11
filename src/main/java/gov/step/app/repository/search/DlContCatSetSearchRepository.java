package gov.step.app.repository.search;

import gov.step.app.domain.DlContCatSet;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the DlContCatSet entity.
 */
public interface DlContCatSetSearchRepository extends ElasticsearchRepository<DlContCatSet, Long> {
}
