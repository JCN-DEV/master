package gov.step.app.repository.search;

import gov.step.app.domain.VclVehicleDriverAssign;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the VclVehicleDriverAssign entity.
 */
public interface VclVehicleDriverAssignSearchRepository extends ElasticsearchRepository<VclVehicleDriverAssign, Long> {
}
