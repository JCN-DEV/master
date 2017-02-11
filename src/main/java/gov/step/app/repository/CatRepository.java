
package gov.step.app.repository;
import gov.step.app.domain.Cat;
import gov.step.app.domain.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Cat entity.
 */
public interface CatRepository extends JpaRepository<Cat, Long> {

    public Page<Cat> findAllByOrderByIdDesc(Pageable pageable);

    @Query("SELECT cat from Cat cat where lower(cat.cat) = :name")
    Cat findOneByName(@Param("name") String name);

    @Query("SELECT cat from Cat cat where cat.status=true and (cat.organizationCategory is null or cat.organizationCategory.id = :id)")
    List<Cat> findOneByOrganizationCatId(@Param("id") Long id);


}
