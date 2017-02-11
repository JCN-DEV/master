package gov.step.app.repository.search;

import gov.step.app.domain.NameCnclApplication;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the NameCnclApplication entity.
 */
public interface NameCnclApplicationSearchRepository extends ElasticsearchRepository<NameCnclApplication, Long> {
}
