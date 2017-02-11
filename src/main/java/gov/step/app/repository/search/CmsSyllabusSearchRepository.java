package gov.step.app.repository.search;

import gov.step.app.domain.CmsSyllabus;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the CmsSyllabus entity.
 */
public interface CmsSyllabusSearchRepository extends ElasticsearchRepository<CmsSyllabus, Long> {
}
