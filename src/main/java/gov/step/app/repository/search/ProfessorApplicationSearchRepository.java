package gov.step.app.repository.search;

import gov.step.app.domain.ProfessorApplication;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ProfessorApplication entity.
 */
public interface ProfessorApplicationSearchRepository extends ElasticsearchRepository<ProfessorApplication, Long> {
}
