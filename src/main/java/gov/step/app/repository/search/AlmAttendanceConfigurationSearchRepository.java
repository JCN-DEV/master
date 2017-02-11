package gov.step.app.repository.search;

import gov.step.app.domain.AlmAttendanceConfiguration;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the AlmAttendanceConfiguration entity.
 */
public interface AlmAttendanceConfigurationSearchRepository extends ElasticsearchRepository<AlmAttendanceConfiguration, Long> {
}
