package gov.step.app.repository.search;

import gov.step.app.domain.DlBookIssue;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the DlBookIssue entity.
 */
public interface DlBookIssueSearchRepository extends ElasticsearchRepository<DlBookIssue, Long> {
}
