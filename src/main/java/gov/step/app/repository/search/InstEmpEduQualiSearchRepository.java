package gov.step.app.repository.search;

import gov.step.app.domain.InstEmpEduQuali;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstEmpEduQuali entity.
 */
public interface InstEmpEduQualiSearchRepository extends ElasticsearchRepository<InstEmpEduQuali, Long> {
}
