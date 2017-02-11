package gov.step.app.repository.search;

import gov.step.app.domain.AlmEmpLeaveGroupMap;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the AlmEmpLeaveGroupMap entity.
 */
public interface AlmEmpLeaveGroupMapSearchRepository extends ElasticsearchRepository<AlmEmpLeaveGroupMap, Long> {
}
