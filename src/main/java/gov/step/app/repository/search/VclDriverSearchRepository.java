package gov.step.app.repository.search;

import gov.step.app.domain.VclDriver;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the VclDriver entity.
 */
public interface VclDriverSearchRepository extends ElasticsearchRepository<VclDriver, Long> {
}
