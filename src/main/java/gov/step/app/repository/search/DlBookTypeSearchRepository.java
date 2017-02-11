package gov.step.app.repository.search;

import gov.step.app.domain.DlBookType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the DlBookType entity.
 */
public interface DlBookTypeSearchRepository extends ElasticsearchRepository<DlBookType, Long> {
}
