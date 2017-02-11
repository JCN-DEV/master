package gov.step.app.repository.search;

import gov.step.app.domain.Department;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Department entity.
 */
public interface DepartmentSearchRepository extends ElasticsearchRepository<Department, Long> {
}
