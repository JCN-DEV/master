package gov.step.app.repository.search;

import gov.step.app.domain.Reference;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the Reference entity.
 */
public interface ReferenceSearchRepository extends ElasticsearchRepository<Reference, Long> {
}
