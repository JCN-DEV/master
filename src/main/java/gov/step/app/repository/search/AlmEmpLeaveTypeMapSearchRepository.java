package gov.step.app.repository.search;

import gov.step.app.domain.AlmEmpLeaveTypeMap;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the AlmEmpLeaveTypeMap entity.
 */
public interface AlmEmpLeaveTypeMapSearchRepository extends ElasticsearchRepository<AlmEmpLeaveTypeMap, Long> {
}
