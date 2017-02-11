package gov.step.app.repository.search;

import gov.step.app.domain.RisNewAppForm;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the RisNewAppForm entity.
 */
public interface RisNewAppFormSearchRepository extends ElasticsearchRepository<RisNewAppForm, Long> {
}
