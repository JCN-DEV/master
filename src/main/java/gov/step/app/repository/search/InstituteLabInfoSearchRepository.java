package gov.step.app.repository.search;

import gov.step.app.domain.InstituteLabInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstituteLabInfo entity.
 */
public interface InstituteLabInfoSearchRepository extends ElasticsearchRepository<InstituteLabInfo, Long> {
}
