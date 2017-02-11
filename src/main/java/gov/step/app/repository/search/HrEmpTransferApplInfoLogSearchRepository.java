package gov.step.app.repository.search;

import gov.step.app.domain.HrEmpTransferApplInfoLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the HrEmpTransferApplInfoLog entity.
 */
public interface HrEmpTransferApplInfoLogSearchRepository extends ElasticsearchRepository<HrEmpTransferApplInfoLog, Long> {
}
