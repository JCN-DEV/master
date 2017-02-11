package gov.step.app.repository.search;

import gov.step.app.domain.InsAcademicInfoTemp;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InsAcademicInfoTemp entity.
 */
public interface InsAcademicInfoTempSearchRepository extends ElasticsearchRepository<InsAcademicInfoTemp, Long> {
}
