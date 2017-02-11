package gov.step.app.repository.search;

import gov.step.app.domain.ApplicantEducation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the ApplicantEducation entity.
 */
public interface ApplicantEducationSearchRepository extends ElasticsearchRepository<ApplicantEducation, Long> {
}
