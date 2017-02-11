package gov.step.app.repository.search;

import gov.step.app.domain.InstVacancyTemp;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstVacancyTemp entity.
 */
public interface InstVacancyTempSearchRepository extends ElasticsearchRepository<InstVacancyTemp, Long> {
}
