package gov.step.app.repository.search;

import gov.step.app.domain.DlBookReturn;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the DlBookReturn entity.
 */
public interface DlBookReturnSearchRepository extends ElasticsearchRepository<DlBookReturn, Long> {
}
