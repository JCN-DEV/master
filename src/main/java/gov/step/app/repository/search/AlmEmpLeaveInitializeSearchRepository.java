package gov.step.app.repository.search;

import gov.step.app.domain.AlmEmpLeaveInitialize;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the AlmEmpLeaveInitialize entity.
 */
public interface AlmEmpLeaveInitializeSearchRepository extends ElasticsearchRepository<AlmEmpLeaveInitialize, Long> {
}
