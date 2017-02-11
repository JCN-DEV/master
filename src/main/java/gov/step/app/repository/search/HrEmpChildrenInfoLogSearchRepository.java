package gov.step.app.repository.search;

import gov.step.app.domain.HrEmpChildrenInfoLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the HrEmpChildrenInfoLog entity.
 */
public interface HrEmpChildrenInfoLogSearchRepository extends ElasticsearchRepository<HrEmpChildrenInfoLog, Long> {
}
