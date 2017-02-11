package gov.step.app.repository.search;

import gov.step.app.domain.QrcodeGenLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the QrcodeGenLog entity.
 */
public interface QrcodeGenLogSearchRepository extends ElasticsearchRepository<QrcodeGenLog, Long> {
}
