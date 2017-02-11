package gov.step.app.repository.search;

import gov.step.app.domain.HrDepartmentalProceeding;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the HrDepartmentalProceeding entity.
 */
public interface HrDepartmentalProceedingSearchRepository extends ElasticsearchRepository<HrDepartmentalProceeding, Long> {
}
