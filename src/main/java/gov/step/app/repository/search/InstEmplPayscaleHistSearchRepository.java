package gov.step.app.repository.search;

import gov.step.app.domain.InstEmplPayscaleHist;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstEmplPayscaleHist entity.
 */
public interface InstEmplPayscaleHistSearchRepository extends ElasticsearchRepository<InstEmplPayscaleHist, Long> {
}
