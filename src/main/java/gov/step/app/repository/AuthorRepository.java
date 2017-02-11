package gov.step.app.repository;

import gov.step.app.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Spring Data JPA repository for the Author entity.
 */
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("select author from Author author where author.user.login = ?#{principal.username}")
    List<Author> findByUserIsCurrentUser();

}
