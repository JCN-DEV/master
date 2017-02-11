package gov.step.app.repository.search;

import gov.step.app.domain.SisStudentInfoSubj;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the SisStudentInfoSubj entity.
 */
public interface SisStudentInfoSubjSearchRepository extends ElasticsearchRepository<SisStudentInfoSubj, Long> {
}
