package gov.step.app.repository.search;

import gov.step.app.domain.HrEmpAcrInfoLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the HrEmpAcrInfoLog entity.
 */
public interface HrEmpAcrInfoLogSearchRepository extends ElasticsearchRepository<HrEmpAcrInfoLog, Long> {
}
