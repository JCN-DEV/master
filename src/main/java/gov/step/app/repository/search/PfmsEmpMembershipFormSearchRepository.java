package gov.step.app.repository.search;

import gov.step.app.domain.PfmsEmpMembershipForm;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PfmsEmpMembershipForm entity.
 */
public interface PfmsEmpMembershipFormSearchRepository extends ElasticsearchRepository<PfmsEmpMembershipForm, Long> {
}
