package gov.step.app.repository.search;

import gov.step.app.domain.AclObjectIdentity;


import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;





/**
 * Spring Data Elasticsearch repository for the AclObjectIdentity entity.
 */
public interface AclObjectIdentitySearchRepository extends ElasticsearchRepository<AclObjectIdentity, Long> {
}
