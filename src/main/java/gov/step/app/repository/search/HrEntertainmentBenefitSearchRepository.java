package gov.step.app.repository.search;

import gov.step.app.domain.HrEntertainmentBenefit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the HrEntertainmentBenefit entity.
 */
public interface HrEntertainmentBenefitSearchRepository extends ElasticsearchRepository<HrEntertainmentBenefit, Long> {
}
