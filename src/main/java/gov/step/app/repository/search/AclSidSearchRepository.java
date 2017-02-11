package gov.step.app.repository.search;

import gov.step.app.domain.AclSid;


import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;





/**
 * Spring Data Elasticsearch repository for the AclSid entity.
 */
public interface AclSidSearchRepository extends ElasticsearchRepository<AclSid, Long> {
}
