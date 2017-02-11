package gov.step.app.repository.search;

import gov.step.app.domain.AlmWorkingUnit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the AlmWorkingUnit entity.
 */
public interface AlmWorkingUnitSearchRepository extends ElasticsearchRepository<AlmWorkingUnit, Long> {
}
