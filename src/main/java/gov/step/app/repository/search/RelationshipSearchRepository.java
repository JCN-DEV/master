package gov.step.app.repository.search;

import gov.step.app.domain.Relationship;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Relationship entity.
 */
public interface RelationshipSearchRepository extends ElasticsearchRepository<Relationship, Long> {
}
