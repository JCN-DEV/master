package gov.step.app.repository.search;

import gov.step.app.domain.RisNewVacancy;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the RisNewVacancy entity.
 */
public interface RisNewVacancySearchRepository extends ElasticsearchRepository<RisNewVacancy, Long> {
}
