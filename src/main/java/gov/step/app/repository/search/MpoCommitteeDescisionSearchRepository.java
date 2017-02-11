package gov.step.app.repository.search;

import gov.step.app.domain.MpoCommitteeDescision;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the MpoCommitteeDescision entity.
 */
public interface MpoCommitteeDescisionSearchRepository extends ElasticsearchRepository<MpoCommitteeDescision, Long> {
}
