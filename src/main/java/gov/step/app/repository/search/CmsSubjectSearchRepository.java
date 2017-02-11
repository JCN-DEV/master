package gov.step.app.repository.search;

import gov.step.app.domain.CmsSubject;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the CmsSubject entity.
 */
public interface CmsSubjectSearchRepository extends ElasticsearchRepository<CmsSubject, Long> {
}
