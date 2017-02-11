package gov.step.app.repository.search;

import gov.step.app.domain.InstEmplRecruitInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstEmplRecruitInfo entity.
 */
public interface InstEmplRecruitInfoSearchRepository extends ElasticsearchRepository<InstEmplRecruitInfo, Long> {
}
