package gov.step.app.repository.search;

import gov.step.app.domain.AlmOnDutyLeaveApp;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the AlmOnDutyLeaveApp entity.
 */
public interface AlmOnDutyLeaveAppSearchRepository extends ElasticsearchRepository<AlmOnDutyLeaveApp, Long> {
}
