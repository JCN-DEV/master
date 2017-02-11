package gov.step.app.repository.search;

import gov.step.app.domain.PgmsAppFamilyPension;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PgmsAppFamilyPension entity.
 */
public interface PgmsAppFamilyPensionSearchRepository extends ElasticsearchRepository<PgmsAppFamilyPension, Long> {
}
