package gov.step.app.repository.search;

import gov.step.app.domain.AlmEmpLeaveApplication;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the AlmEmpLeaveApplication entity.
 */
public interface AlmEmpLeaveApplicationSearchRepository extends ElasticsearchRepository<AlmEmpLeaveApplication, Long> {
}
