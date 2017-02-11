package gov.step.app.repository.search.payroll;

import gov.step.app.domain.payroll.PrlEmpGenSalDetailInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PrlEmpGenSalDetailInfo entity.
 */
public interface PrlEmpGenSalDetailInfoSearchRepository extends ElasticsearchRepository<PrlEmpGenSalDetailInfo, Long> {
}
