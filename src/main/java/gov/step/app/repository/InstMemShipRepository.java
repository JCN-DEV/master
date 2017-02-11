package gov.step.app.repository;

import gov.step.app.domain.InstLevel;
import gov.step.app.domain.InstMemShip;

import gov.step.app.domain.Institute;
import gov.step.app.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the InstMemShip entity.
 */
public interface InstMemShipRepository extends JpaRepository<InstMemShip,Long> {

    public Page<InstMemShip> findAllByOrderByIdDesc(Pageable pageable);


    @Query("select instMemShip from InstMemShip instMemShip where instMemShip.email = :email")
    Optional<InstMemShip> findOneByEmail(@Param("email") String email);

    @Query("select instMemShip from InstMemShip instMemShip where instMemShip.institute.id = :id")
    Page<InstMemShip> findAllByInstitute(Pageable pageable,@Param("id") Long id);

    @Query("select instMemShip.user from InstMemShip instMemShip where instMemShip.institute.id = :id and instMemShip.user.activated = true")
    List<User> findAllActiveUserByInstitute(@Param("id") Long id);

    @Query("select instMemShip from InstMemShip instMemShip where instMemShip.institute.id = :id and instMemShip.email = :email")
    InstMemShip findOneByInstituteAndEmail(@Param("id") Long id,@Param("email") String email);

    @Query("select instMemShip.institute from InstMemShip instMemShip where instMemShip.user.login = ?#{principal.username}")
    Institute findCurrentMemberInstitute();



}
