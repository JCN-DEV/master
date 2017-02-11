package gov.step.app.repository.search;

import gov.step.app.domain.InstEmplDesignation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstEmplDesignation entity.
 */
public interface InstEmplDesignationSearchRepository extends ElasticsearchRepository<InstEmplDesignation, Long> {
}
