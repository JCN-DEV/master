package gov.step.app.repository.search;

import gov.step.app.domain.MpoVacancyRoleTrade;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the MpoVacancyRoleTrade entity.
 */
public interface MpoVacancyRoleTradeSearchRepository extends ElasticsearchRepository<MpoVacancyRoleTrade, Long> {
}
