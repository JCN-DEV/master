package gov.step.app.repository.search;

import gov.step.app.domain.DlContentUpload;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the DlContentUpload entity.
 */
public interface DlContentUploadSearchRepository extends ElasticsearchRepository<DlContentUpload, Long> {
}
