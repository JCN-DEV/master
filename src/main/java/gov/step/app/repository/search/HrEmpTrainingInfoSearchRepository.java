package gov.step.app.repository.search;

import gov.step.app.domain.HrEmpTrainingInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the HrEmpTrainingInfo entity.
 */
public interface HrEmpTrainingInfoSearchRepository extends ElasticsearchRepository<HrEmpTrainingInfo, Long> {
}
