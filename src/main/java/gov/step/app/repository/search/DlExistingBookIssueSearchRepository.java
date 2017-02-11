package gov.step.app.repository.search;

import gov.step.app.domain.DlExistingBookIssue;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the DlExistingBookIssue entity.
 */
public interface DlExistingBookIssueSearchRepository extends ElasticsearchRepository<DlExistingBookIssue, Long> {
}
