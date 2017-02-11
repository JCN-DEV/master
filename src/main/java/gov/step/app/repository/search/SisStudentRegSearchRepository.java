package gov.step.app.repository.search;

import gov.step.app.domain.SisStudentReg;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the SisStudentReg entity.
 */
public interface SisStudentRegSearchRepository extends ElasticsearchRepository<SisStudentReg, Long> {
}
