package gov.step.app.repository.search;

import gov.step.app.domain.Designation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Designation entity.
 */
public interface DesignationSearchRepository extends ElasticsearchRepository<Designation, Long> {
}
