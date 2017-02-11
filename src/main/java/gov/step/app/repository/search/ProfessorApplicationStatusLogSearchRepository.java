package gov.step.app.repository.search;

import gov.step.app.domain.ProfessorApplicationStatusLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ProfessorApplicationStatusLog entity.
 */
public interface ProfessorApplicationStatusLogSearchRepository extends ElasticsearchRepository<ProfessorApplicationStatusLog, Long> {
}
