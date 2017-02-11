package gov.step.app.repository.search;

import gov.step.app.domain.AuditLogHistory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the AuditLogHistory entity.
 */
public interface AuditLogHistorySearchRepository extends ElasticsearchRepository<AuditLogHistory, Long> {
}
