package gov.step.app.repository.search;

import gov.step.app.domain.PgmsPenGrCalculation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PgmsPenGrCalculation entity.
 */
public interface PgmsPenGrCalculationSearchRepository extends ElasticsearchRepository<PgmsPenGrCalculation, Long> {
}
