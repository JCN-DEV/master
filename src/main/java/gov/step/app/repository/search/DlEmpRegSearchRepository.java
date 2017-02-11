package gov.step.app.repository.search;

import gov.step.app.domain.DlEmpReg;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the DlEmpReg entity.
 */
public interface DlEmpRegSearchRepository extends ElasticsearchRepository<DlEmpReg, Long> {
}
