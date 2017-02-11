package gov.step.app.repository.search;

import gov.step.app.domain.BEdApplication;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the BEdApplication entity.
 */
public interface BEdApplicationSearchRepository extends ElasticsearchRepository<BEdApplication, Long> {
}
