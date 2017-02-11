package gov.step.app.repository.search;

import gov.step.app.domain.VclEmployeeAssign;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the VclEmployeeAssign entity.
 */
public interface VclEmployeeAssignSearchRepository extends ElasticsearchRepository<VclEmployeeAssign, Long> {
}
