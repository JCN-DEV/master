package gov.step.app.repository.search;

import gov.step.app.domain.VclVehicle;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the VclVehicle entity.
 */
public interface VclVehicleSearchRepository extends ElasticsearchRepository<VclVehicle, Long> {
}
