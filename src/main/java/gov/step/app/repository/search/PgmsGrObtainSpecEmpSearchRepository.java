package gov.step.app.repository.search;

import gov.step.app.domain.PgmsGrObtainSpecEmp;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PgmsGrObtainSpecEmp entity.
 */
public interface PgmsGrObtainSpecEmpSearchRepository extends ElasticsearchRepository<PgmsGrObtainSpecEmp, Long> {
}
