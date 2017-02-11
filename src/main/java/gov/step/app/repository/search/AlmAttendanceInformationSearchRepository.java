package gov.step.app.repository.search;

import gov.step.app.domain.AlmAttendanceInformation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the AlmAttendanceInformation entity.
 */
public interface AlmAttendanceInformationSearchRepository extends ElasticsearchRepository<AlmAttendanceInformation, Long> {
}
