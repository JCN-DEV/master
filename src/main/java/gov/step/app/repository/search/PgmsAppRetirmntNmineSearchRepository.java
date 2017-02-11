package gov.step.app.repository.search;

import gov.step.app.domain.PgmsAppRetirmntNmine;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PgmsAppRetirmntNmine entity.
 */
public interface PgmsAppRetirmntNmineSearchRepository extends ElasticsearchRepository<PgmsAppRetirmntNmine, Long> {
}
