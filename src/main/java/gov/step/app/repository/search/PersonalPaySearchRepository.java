package gov.step.app.repository.search;

import gov.step.app.domain.PersonalPay;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PersonalPay entity.
 */
public interface PersonalPaySearchRepository extends ElasticsearchRepository<PersonalPay, Long> {
}
