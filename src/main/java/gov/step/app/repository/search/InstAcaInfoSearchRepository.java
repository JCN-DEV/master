package gov.step.app.repository.search;

import gov.step.app.domain.InstAcaInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstAcaInfo entity.
 */
public interface InstAcaInfoSearchRepository extends ElasticsearchRepository<InstAcaInfo, Long> {
}
