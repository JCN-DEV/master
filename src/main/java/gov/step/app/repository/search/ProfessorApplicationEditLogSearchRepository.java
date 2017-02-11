package gov.step.app.repository.search;

import gov.step.app.domain.ProfessorApplicationEditLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ProfessorApplicationEditLog entity.
 */
public interface ProfessorApplicationEditLogSearchRepository extends ElasticsearchRepository<ProfessorApplicationEditLog, Long> {
}
