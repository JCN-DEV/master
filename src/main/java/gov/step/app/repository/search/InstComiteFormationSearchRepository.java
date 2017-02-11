package gov.step.app.repository.search;

import gov.step.app.domain.InstComiteFormation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstComiteFormation entity.
 */
public interface InstComiteFormationSearchRepository extends ElasticsearchRepository<InstComiteFormation, Long> {
}
