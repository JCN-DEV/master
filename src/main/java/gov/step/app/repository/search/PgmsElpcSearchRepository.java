package gov.step.app.repository.search;

import gov.step.app.domain.PgmsElpc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PgmsElpc entity.
 */
public interface PgmsElpcSearchRepository extends ElasticsearchRepository<PgmsElpc, Long> {
}
