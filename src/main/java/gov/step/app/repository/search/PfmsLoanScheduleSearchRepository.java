package gov.step.app.repository.search;

import gov.step.app.domain.PfmsLoanSchedule;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PfmsLoanSchedule entity.
 */
public interface PfmsLoanScheduleSearchRepository extends ElasticsearchRepository<PfmsLoanSchedule, Long> {
}
