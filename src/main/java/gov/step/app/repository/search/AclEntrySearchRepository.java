package gov.step.app.repository.search;

import gov.step.app.domain.AclEntry;


import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;





/**
 * Spring Data Elasticsearch repository for the AclEntry entity.
 */
public interface AclEntrySearchRepository extends ElasticsearchRepository<AclEntry, Long> {
}
