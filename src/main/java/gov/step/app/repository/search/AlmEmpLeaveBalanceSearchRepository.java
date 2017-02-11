package gov.step.app.repository.search;

import gov.step.app.domain.AlmEmpLeaveBalance;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the AlmEmpLeaveBalance entity.
 */
public interface AlmEmpLeaveBalanceSearchRepository extends ElasticsearchRepository<AlmEmpLeaveBalance, Long> {
}
