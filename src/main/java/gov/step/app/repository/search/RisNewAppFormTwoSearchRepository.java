package gov.step.app.repository.search;

import gov.step.app.domain.RisNewAppFormTwo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the RisNewAppFormTwo entity.
 */
public interface RisNewAppFormTwoSearchRepository extends ElasticsearchRepository<RisNewAppFormTwo, Long> {
}
