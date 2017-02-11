package gov.step.app.repository.search;

import gov.step.app.domain.InstEmplBankInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstEmplBankInfo entity.
 */
public interface InstEmplBankInfoSearchRepository extends ElasticsearchRepository<InstEmplBankInfo, Long> {
}
