package gov.step.app.repository.search;

import gov.step.app.domain.CmsSubAssign;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the CmsSubAssign entity.
 */
public interface CmsSubAssignSearchRepository extends ElasticsearchRepository<CmsSubAssign, Long> {
}
