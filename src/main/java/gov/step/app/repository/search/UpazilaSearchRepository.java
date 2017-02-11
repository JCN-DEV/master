package gov.step.app.repository.search;

import gov.step.app.domain.Upazila;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Upazila entity.
 */
public interface UpazilaSearchRepository extends ElasticsearchRepository<Upazila, Long> {
}
