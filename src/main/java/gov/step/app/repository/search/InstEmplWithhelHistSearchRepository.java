package gov.step.app.repository.search;

import gov.step.app.domain.InstEmplWithhelHist;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstEmplWithhelHist entity.
 */
public interface InstEmplWithhelHistSearchRepository extends ElasticsearchRepository<InstEmplWithhelHist, Long> {
}
