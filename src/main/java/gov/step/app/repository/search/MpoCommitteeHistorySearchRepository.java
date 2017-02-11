package gov.step.app.repository.search;

import gov.step.app.domain.MpoCommitteeHistory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the MpoCommitteeHistory entity.
 */
public interface MpoCommitteeHistorySearchRepository extends ElasticsearchRepository<MpoCommitteeHistory, Long> {
}
