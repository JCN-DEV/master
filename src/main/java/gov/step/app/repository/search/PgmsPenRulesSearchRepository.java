package gov.step.app.repository.search;

import gov.step.app.domain.PgmsPenRules;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PgmsPenRules entity.
 */
public interface PgmsPenRulesSearchRepository extends ElasticsearchRepository<PgmsPenRules, Long> {
}
