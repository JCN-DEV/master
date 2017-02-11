package gov.step.app.repository;

import gov.step.app.domain.Lang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Lang entity.
 */
public interface LangRepository extends JpaRepository<Lang, Long> {

    @Query("select lang from Lang lang where lang.manager.login = ?#{principal.username}")
    List<Lang> findByManagerIsCurrentUser();

    @Query("select lang from Lang lang where lang.employee.id = :id")
    List<Lang> findByEmployee(@Param("id") Long id);

}
