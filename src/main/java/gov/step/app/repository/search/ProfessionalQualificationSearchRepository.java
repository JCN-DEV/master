package gov.step.app.repository.search;

import gov.step.app.domain.ProfessionalQualification;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ProfessionalQualification entity.
 */
public interface ProfessionalQualificationSearchRepository extends ElasticsearchRepository<ProfessionalQualification, Long> {
}
