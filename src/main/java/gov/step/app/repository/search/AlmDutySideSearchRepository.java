package gov.step.app.repository.search;

import gov.step.app.domain.AlmDutySide;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the AlmDutySide entity.
 */
public interface AlmDutySideSearchRepository extends ElasticsearchRepository<AlmDutySide, Long> {
}
