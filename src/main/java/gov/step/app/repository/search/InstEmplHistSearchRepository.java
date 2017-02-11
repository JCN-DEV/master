package gov.step.app.repository.search;

import gov.step.app.domain.InstEmplHist;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstEmplHist entity.
 */
public interface InstEmplHistSearchRepository extends ElasticsearchRepository<InstEmplHist, Long> {
}
