package gov.step.app.repository.search;

import gov.step.app.domain.EduBoard;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the EduBoard entity.
 */
public interface EduBoardSearchRepository extends ElasticsearchRepository<EduBoard, Long> {
}
