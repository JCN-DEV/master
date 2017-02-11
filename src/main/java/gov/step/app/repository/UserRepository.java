package gov.step.app.repository;

import gov.step.app.domain.Authority;
import gov.step.app.domain.NotificationStep;
import gov.step.app.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the User entity.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOneByActivationKey(String activationKey);

    List<User> findAllByActivatedIsFalseAndCreatedDateBefore(ZonedDateTime dateTime);

    Optional<User> findOneByResetKey(String resetKey);

    Optional<User> findOneByEmail(String email);

    Optional<User> findOneByLogin(String login);

    //Optional<User> findOneByDistrictId(Long districtId);

    Optional<User> findOneById(Long userId);

    @Query("select user from User user where user.activated = :activated")
    Page<User> findAllBlockedUser(Pageable pageable, @Param("activated") Boolean activated);

    //get all users of a specific authority name /role
    @Query("select user from User user join user.authorities auth where auth.name = :role")
    Page<User> findAllUsersByAuthority(Pageable pageable, @Param("role") String role);

    @Override
    void delete(User t);

}
