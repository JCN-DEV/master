package gov.step.app.repository.search;

import gov.step.app.domain.Employer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the Employer entity.
 */
public interface EmployerSearchRepository extends ElasticsearchRepository<Employer, Long> {
}
