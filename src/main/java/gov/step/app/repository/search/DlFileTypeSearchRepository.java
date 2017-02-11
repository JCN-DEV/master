package gov.step.app.repository.search;

import gov.step.app.domain.DlFileType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the DlFileType entity.
 */
public interface DlFileTypeSearchRepository extends ElasticsearchRepository<DlFileType, Long> {
}
