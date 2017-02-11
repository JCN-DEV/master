package gov.step.app.repository.search;

import gov.step.app.domain.EduLevel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the EduLevel entity.
 */
public interface EduLevelSearchRepository extends ElasticsearchRepository<EduLevel, Long> {
}
