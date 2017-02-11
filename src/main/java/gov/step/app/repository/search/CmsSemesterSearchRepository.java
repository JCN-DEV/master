package gov.step.app.repository.search;

import gov.step.app.domain.CmsSemester;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the CmsSemester entity.
 */
public interface CmsSemesterSearchRepository extends ElasticsearchRepository<CmsSemester, Long> {
}
