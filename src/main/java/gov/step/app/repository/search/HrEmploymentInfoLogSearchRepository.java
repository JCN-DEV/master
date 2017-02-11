package gov.step.app.repository.search;

import gov.step.app.domain.HrEmploymentInfoLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the HrEmploymentInfoLog entity.
 */
public interface HrEmploymentInfoLogSearchRepository extends ElasticsearchRepository<HrEmploymentInfoLog, Long> {
}
