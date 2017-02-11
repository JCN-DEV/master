package gov.step.app.repository.search;

import gov.step.app.domain.Lang;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the Lang entity.
 */
public interface LangSearchRepository extends ElasticsearchRepository<Lang, Long> {
}
