package gov.step.app.repository.search;

import gov.step.app.domain.AlmLeaveType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the AlmLeaveType entity.
 */
public interface AlmLeaveTypeSearchRepository extends ElasticsearchRepository<AlmLeaveType, Long> {
}
