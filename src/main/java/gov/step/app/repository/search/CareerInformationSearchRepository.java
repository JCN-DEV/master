package gov.step.app.repository.search;

import gov.step.app.domain.CareerInformation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the CareerInformation entity.
 */
public interface CareerInformationSearchRepository extends ElasticsearchRepository<CareerInformation, Long> {
}
