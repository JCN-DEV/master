package gov.step.app.repository.search;

import gov.step.app.domain.MpoCommitteePersonInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the MpoCommitteePersonInfo entity.
 */
public interface MpoCommitteePersonInfoSearchRepository extends ElasticsearchRepository<MpoCommitteePersonInfo, Long> {
}
