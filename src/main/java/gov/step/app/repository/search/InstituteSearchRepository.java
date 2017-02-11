package gov.step.app.repository.search;

import gov.step.app.domain.Institute;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the Institute entity.
 */
public interface InstituteSearchRepository extends ElasticsearchRepository<Institute, Long> {
}
