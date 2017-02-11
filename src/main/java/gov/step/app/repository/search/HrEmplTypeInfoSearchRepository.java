package gov.step.app.repository.search;

import gov.step.app.domain.HrEmplTypeInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the HrEmplTypeInfo entity.
 */
public interface HrEmplTypeInfoSearchRepository extends ElasticsearchRepository<HrEmplTypeInfo, Long> {
}
