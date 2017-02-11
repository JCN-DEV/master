package gov.step.app.repository.search;

import gov.step.app.domain.LecturerSeniority;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the LecturerSeniority entity.
 */
public interface LecturerSenioritySearchRepository extends ElasticsearchRepository<LecturerSeniority, Long> {
}
