package gov.step.app.repository.search;

import gov.step.app.domain.InstEmpSpouseInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstEmpSpouseInfo entity.
 */
public interface InstEmpSpouseInfoSearchRepository extends ElasticsearchRepository<InstEmpSpouseInfo, Long> {
}
