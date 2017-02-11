package gov.step.app.repository.search;

import gov.step.app.domain.HrClassInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the HrClassInfo entity.
 */
public interface HrClassInfoSearchRepository extends ElasticsearchRepository<HrClassInfo, Long> {
}
