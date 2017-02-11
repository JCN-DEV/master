package gov.step.app.repository.search;

import gov.step.app.domain.AlmLeavGrpTypeMap;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the AlmLeavGrpTypeMap entity.
 */
public interface AlmLeavGrpTypeMapSearchRepository extends ElasticsearchRepository<AlmLeavGrpTypeMap, Long> {
}
