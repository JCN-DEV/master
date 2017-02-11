package gov.step.app.repository.search;

import gov.step.app.domain.PgmsPenGrRate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PgmsPenGrRate entity.
 */
public interface PgmsPenGrRateSearchRepository extends ElasticsearchRepository<PgmsPenGrRate, Long> {
}
