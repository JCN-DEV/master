package gov.step.app.repository.search;

import gov.step.app.domain.InstituteLand;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstituteLand entity.
 */
public interface InstituteLandSearchRepository extends ElasticsearchRepository<InstituteLand, Long> {
}
