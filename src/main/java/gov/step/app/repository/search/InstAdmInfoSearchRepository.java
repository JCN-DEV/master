package gov.step.app.repository.search;

import gov.step.app.domain.InstAdmInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstAdmInfo entity.
 */
public interface InstAdmInfoSearchRepository extends ElasticsearchRepository<InstAdmInfo, Long> {
}
