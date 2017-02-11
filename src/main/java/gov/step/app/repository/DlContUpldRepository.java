package gov.step.app.repository;

import gov.step.app.domain.*;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the DlContUpld entity.
 */
public interface DlContUpldRepository extends JpaRepository<DlContUpld,Long> {


    @Query("select dlContUpld from DlContUpld dlContUpld where dlContUpld.dlContCatSet.id = :dlContCatSet AND dlContUpld.dlContSCatSet.id = :dlContSCatSet AND dlContUpld.dlContTypeSet.id = :dlContTypeSet ")
    List<DlContUpld> findAllByAllType(@Param("dlContCatSet") Long dlContCatSet, @Param("dlContSCatSet") Long dlContSCatSet, @Param("dlContTypeSet") Long dlContTypeSet);


    @Query("select dlContUpld from DlContUpld dlContUpld where dlContUpld.status = 2 AND dlContUpld.instEmployee.institute.user.login = ?#{principal.username}")
    List<DlContUpld> findAllContUpldsByUserIsCurrentUser();

    @Query("select dlContUpld from DlContUpld dlContUpld where dlContUpld.status = 4 ")
   Page <DlContUpld> findAllbyAdmin(org.springframework.data.domain.Pageable pageable);

    @Query("select dlContUpld from DlContUpld dlContUpld where dlContUpld.status = 5 ")
    Page <DlContUpld> findAllbyUser(org.springframework.data.domain.Pageable pageable);

    //this query is for category
    @Query("select dlContUpld from DlContUpld dlContUpld where dlContUpld.dlContCatSet.id =:id ")
    List<DlContUpld> findAllById(@Param("id") Long id);

    @Query("select dlContUpld from DlContUpld dlContUpld where dlContUpld.dlContSCatSet.id =:id ")
    List<DlContUpld> findAllBySCategory(@Param("id") Long id);

    @Query("select dlContUpld from DlContUpld dlContUpld where dlContUpld.dlContTypeSet.id =:id ")
    List<DlContUpld> findAllByType(@Param("id") Long id);
    //end of category


    @Query("select dlContUpld from DlContUpld dlContUpld where dlContUpld.code = :code")
    Optional<DlContUpld> findOneByCode(@Param("code") String code);

    @Query("select dlContUpld from DlContUpld dlContUpld where dlContUpld.isbnNo = :isbnNo")
    Optional<DlContUpld> findOneByIsbn(@Param("isbnNo") String isbnNo);



}
