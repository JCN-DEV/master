package gov.step.app.repository.search;

import gov.step.app.domain.HrSpouseInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the HrSpouseInfo entity.
 */
public interface HrSpouseInfoSearchRepository extends ElasticsearchRepository<HrSpouseInfo, Long> {
}
