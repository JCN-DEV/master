package gov.step.app.repository.search;

import gov.step.app.domain.AttachmentCategory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the AttachmentCategory entity.
 */
public interface AttachmentCategorySearchRepository extends ElasticsearchRepository<AttachmentCategory, Long> {
}
