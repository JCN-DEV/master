package gov.step.app.repository.search;

import gov.step.app.domain.InstituteEmpCount;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstituteEmpCount entity.
 */
public interface InstituteEmpCountSearchRepository extends ElasticsearchRepository<InstituteEmpCount, Long> {
}
