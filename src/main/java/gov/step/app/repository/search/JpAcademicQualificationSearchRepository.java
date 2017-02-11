package gov.step.app.repository.search;

import gov.step.app.domain.JpAcademicQualification;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the JpAcademicQualification entity.
 */
public interface JpAcademicQualificationSearchRepository extends ElasticsearchRepository<JpAcademicQualification, Long> {
}
