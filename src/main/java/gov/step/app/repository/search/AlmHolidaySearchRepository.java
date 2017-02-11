package gov.step.app.repository.search;

import gov.step.app.domain.AlmHoliday;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the AlmHoliday entity.
 */
public interface AlmHolidaySearchRepository extends ElasticsearchRepository<AlmHoliday, Long> {
}
