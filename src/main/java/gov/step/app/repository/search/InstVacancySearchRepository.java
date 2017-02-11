package gov.step.app.repository.search;

import gov.step.app.domain.InstVacancy;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstVacancy entity.
 */
public interface InstVacancySearchRepository extends ElasticsearchRepository<InstVacancy, Long> {
}
