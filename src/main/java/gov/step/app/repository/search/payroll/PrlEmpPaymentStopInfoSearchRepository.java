package gov.step.app.repository.search.payroll;

import gov.step.app.domain.payroll.PrlEmpPaymentStopInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PrlEmpPaymentStopInfo entity.
 */
public interface PrlEmpPaymentStopInfoSearchRepository extends ElasticsearchRepository<PrlEmpPaymentStopInfo, Long> {
}
