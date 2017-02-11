package gov.step.app.repository.search;

import gov.step.app.domain.InstComiteFormationTemp;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstComiteFormationTemp entity.
 */
public interface InstComiteFormationTempSearchRepository extends ElasticsearchRepository<InstComiteFormationTemp, Long> {
}
