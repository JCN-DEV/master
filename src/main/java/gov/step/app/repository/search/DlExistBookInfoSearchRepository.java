package gov.step.app.repository.search;

import gov.step.app.domain.DlExistBookInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the DlExistBookInfo entity.
 */
public interface DlExistBookInfoSearchRepository extends ElasticsearchRepository<DlExistBookInfo, Long> {
}
