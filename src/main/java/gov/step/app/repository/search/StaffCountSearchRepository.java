package gov.step.app.repository.search;

import gov.step.app.domain.StaffCount;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the StaffCount entity.
 */
public interface StaffCountSearchRepository extends ElasticsearchRepository<StaffCount, Long> {
}
