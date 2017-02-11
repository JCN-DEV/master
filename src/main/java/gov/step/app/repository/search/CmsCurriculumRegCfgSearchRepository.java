package gov.step.app.repository.search;

import gov.step.app.domain.CmsCurriculumRegCfg;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the CmsCurriculumRegCfg entity.
 */
public interface CmsCurriculumRegCfgSearchRepository extends ElasticsearchRepository<CmsCurriculumRegCfg, Long> {
}
