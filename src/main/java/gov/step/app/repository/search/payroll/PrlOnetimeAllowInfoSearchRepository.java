package gov.step.app.repository.search.payroll;

import gov.step.app.domain.payroll.PrlOnetimeAllowInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PrlOnetimeAllowInfo entity.
 */
public interface PrlOnetimeAllowInfoSearchRepository extends ElasticsearchRepository<PrlOnetimeAllowInfo, Long> {
}
