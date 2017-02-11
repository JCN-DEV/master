package gov.step.app.repository.search;

import gov.step.app.domain.PgmsRetirmntAttachInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PgmsRetirmntAttachInfo entity.
 */
public interface PgmsRetirmntAttachInfoSearchRepository extends ElasticsearchRepository<PgmsRetirmntAttachInfo, Long> {
}
