package gov.step.app.repository.search;

import gov.step.app.domain.AlmLeaveRule;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the AlmLeaveRule entity.
 */
public interface AlmLeaveRuleSearchRepository extends ElasticsearchRepository<AlmLeaveRule, Long> {
}
