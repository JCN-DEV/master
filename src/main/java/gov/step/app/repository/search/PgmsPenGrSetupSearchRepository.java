package gov.step.app.repository.search;

import gov.step.app.domain.PgmsPenGrSetup;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PgmsPenGrSetup entity.
 */
public interface PgmsPenGrSetupSearchRepository extends ElasticsearchRepository<PgmsPenGrSetup, Long> {
}
