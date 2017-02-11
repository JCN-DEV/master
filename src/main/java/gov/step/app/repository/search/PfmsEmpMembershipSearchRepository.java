package gov.step.app.repository.search;

import gov.step.app.domain.PfmsEmpMembership;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PfmsEmpMembership entity.
 */
public interface PfmsEmpMembershipSearchRepository extends ElasticsearchRepository<PfmsEmpMembership, Long> {
}
