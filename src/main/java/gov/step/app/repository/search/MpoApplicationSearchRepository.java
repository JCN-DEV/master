package gov.step.app.repository.search;

import gov.step.app.domain.MpoApplication;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the MpoApplication entity.
 */
public interface MpoApplicationSearchRepository extends ElasticsearchRepository<MpoApplication, Long> {
}
