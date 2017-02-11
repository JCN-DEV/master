package gov.step.app.repository.search;

import gov.step.app.domain.EducationalQualification;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the EducationalQualification entity.
 */
public interface EducationalQualificationSearchRepository extends ElasticsearchRepository<EducationalQualification, Long> {
}
