package gov.step.app.repository.search;

import gov.step.app.domain.Vacancy;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the Vacancy entity.
 */
public interface VacancySearchRepository extends ElasticsearchRepository<Vacancy, Long> {
}
