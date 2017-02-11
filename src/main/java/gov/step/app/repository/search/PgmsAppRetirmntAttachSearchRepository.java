package gov.step.app.repository.search;

import gov.step.app.domain.PgmsAppRetirmntAttach;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PgmsAppRetirmntAttach entity.
 */
public interface PgmsAppRetirmntAttachSearchRepository extends ElasticsearchRepository<PgmsAppRetirmntAttach, Long> {
}
