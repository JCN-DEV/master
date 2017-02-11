package gov.step.app.repository.search;

import gov.step.app.domain.DearnessAssign;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the DearnessAssign entity.
 */
public interface DearnessAssignSearchRepository extends ElasticsearchRepository<DearnessAssign, Long> {
}
