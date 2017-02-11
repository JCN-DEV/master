package gov.step.app.repository.search;

import gov.step.app.domain.HrNomineeInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the HrNomineeInfo entity.
 */
public interface HrNomineeInfoSearchRepository extends ElasticsearchRepository<HrNomineeInfo, Long> {
}
