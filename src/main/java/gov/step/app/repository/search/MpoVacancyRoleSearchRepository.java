package gov.step.app.repository.search;

import gov.step.app.domain.MpoVacancyRole;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the MpoVacancyRole entity.
 */
public interface MpoVacancyRoleSearchRepository extends ElasticsearchRepository<MpoVacancyRole, Long> {
}
