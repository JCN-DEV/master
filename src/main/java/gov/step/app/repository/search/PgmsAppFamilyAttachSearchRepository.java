package gov.step.app.repository.search;

import gov.step.app.domain.PgmsAppFamilyAttach;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PgmsAppFamilyAttach entity.
 */
public interface PgmsAppFamilyAttachSearchRepository extends ElasticsearchRepository<PgmsAppFamilyAttach, Long> {
}
