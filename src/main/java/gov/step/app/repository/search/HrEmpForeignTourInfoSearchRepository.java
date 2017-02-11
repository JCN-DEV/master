package gov.step.app.repository.search;

import gov.step.app.domain.HrEmpForeignTourInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the HrEmpForeignTourInfo entity.
 */
public interface HrEmpForeignTourInfoSearchRepository extends ElasticsearchRepository<HrEmpForeignTourInfo, Long> {
}
