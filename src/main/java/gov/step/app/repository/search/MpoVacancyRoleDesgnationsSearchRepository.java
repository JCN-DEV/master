package gov.step.app.repository.search;

import gov.step.app.domain.MpoVacancyRoleDesgnations;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the MpoVacancyRoleDesgnations entity.
 */
public interface MpoVacancyRoleDesgnationsSearchRepository extends ElasticsearchRepository<MpoVacancyRoleDesgnations, Long> {
}
