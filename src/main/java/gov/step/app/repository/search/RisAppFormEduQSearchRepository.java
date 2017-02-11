package gov.step.app.repository.search;

import gov.step.app.domain.RisAppFormEduQ;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the RisAppFormEduQ entity.
 */
public interface RisAppFormEduQSearchRepository extends ElasticsearchRepository<RisAppFormEduQ, Long> {
}
