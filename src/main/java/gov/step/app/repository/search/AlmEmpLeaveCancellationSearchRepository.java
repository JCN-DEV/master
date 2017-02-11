package gov.step.app.repository.search;

import gov.step.app.domain.AlmEmpLeaveCancellation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the AlmEmpLeaveCancellation entity.
 */
public interface AlmEmpLeaveCancellationSearchRepository extends ElasticsearchRepository<AlmEmpLeaveCancellation, Long> {
}
