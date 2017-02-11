package gov.step.app.repository.search;

import gov.step.app.domain.InstituteInfraInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstituteInfraInfo entity.
 */
public interface InstituteInfraInfoSearchRepository extends ElasticsearchRepository<InstituteInfraInfo, Long> {
}
