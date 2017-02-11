package gov.step.app.repository.search;

import gov.step.app.domain.HrEmpServiceHistory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the HrEmpServiceHistory entity.
 */
public interface HrEmpServiceHistorySearchRepository extends ElasticsearchRepository<HrEmpServiceHistory, Long> {
}
