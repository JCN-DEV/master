package gov.step.app.repository.search;

import gov.step.app.domain.AlmLeaveGroup;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the AlmLeaveGroup entity.
 */
public interface AlmLeaveGroupSearchRepository extends ElasticsearchRepository<AlmLeaveGroup, Long> {
}
