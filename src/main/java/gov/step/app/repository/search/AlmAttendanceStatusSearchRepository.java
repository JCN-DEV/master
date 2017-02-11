package gov.step.app.repository.search;

import gov.step.app.domain.AlmAttendanceStatus;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the AlmAttendanceStatus entity.
 */
public interface AlmAttendanceStatusSearchRepository extends ElasticsearchRepository<AlmAttendanceStatus, Long> {
}
