package gov.step.app.repository.search;

import gov.step.app.domain.AlmShiftSetup;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the AlmShiftSetup entity.
 */
public interface AlmShiftSetupSearchRepository extends ElasticsearchRepository<AlmShiftSetup, Long> {
}
