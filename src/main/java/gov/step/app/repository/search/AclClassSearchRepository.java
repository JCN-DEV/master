package gov.step.app.repository.search;

import gov.step.app.domain.AclClass;


import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;





/**
 * Spring Data Elasticsearch repository for the AclClass entity.
 */
public interface AclClassSearchRepository extends ElasticsearchRepository<AclClass, Long> {
}
