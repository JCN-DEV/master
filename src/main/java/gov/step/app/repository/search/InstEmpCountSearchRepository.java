package gov.step.app.repository.search;

import gov.step.app.domain.InstEmpCount;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstEmpCount entity.
 */
public interface InstEmpCountSearchRepository extends ElasticsearchRepository<InstEmpCount, Long> {
}
