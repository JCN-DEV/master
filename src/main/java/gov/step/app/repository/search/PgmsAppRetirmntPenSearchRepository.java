package gov.step.app.repository.search;

import gov.step.app.domain.PgmsAppRetirmntPen;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PgmsAppRetirmntPen entity.
 */
public interface PgmsAppRetirmntPenSearchRepository extends ElasticsearchRepository<PgmsAppRetirmntPen, Long> {
}
