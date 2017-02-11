package gov.step.app.repository.search;

import gov.step.app.domain.DlBookCat;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the DlBookCat entity.
 */
public interface DlBookCatSearchRepository extends ElasticsearchRepository<DlBookCat, Long> {
}
