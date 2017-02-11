package gov.step.app.repository.search;

import gov.step.app.domain.DlBookInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the DlBookInfo entity.
 */
public interface DlBookInfoSearchRepository extends ElasticsearchRepository<DlBookInfo, Long> {
}
