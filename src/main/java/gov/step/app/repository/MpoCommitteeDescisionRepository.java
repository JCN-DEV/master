package gov.step.app.repository;

import gov.step.app.domain.MpoCommitteeDescision;

import gov.step.app.domain.MpoCommitteePersonInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the MpoCommitteeDescision entity.
 */
public interface MpoCommitteeDescisionRepository extends JpaRepository<MpoCommitteeDescision,Long> {

    @Query("select mpoCommitteeDescision from MpoCommitteeDescision mpoCommitteeDescision where mpoCommitteeDescision.mpoApplication.id = :mpoApplication_id and mpoCommitteeDescision.user.id = :userId")
    MpoCommitteeDescision findDescisionByApplicationAndMember(@Param("mpoApplication_id") Long mpoApplicationId, @Param("userId") Long userId);

    @Query("select mpoCommitteeDescision from MpoCommitteeDescision mpoCommitteeDescision where mpoCommitteeDescision.mpoApplication.id = :mpoApplication_id")
    List<MpoCommitteeDescision> findDescisionByApplication(@Param("mpoApplication_id") Long mpoApplicationId);

    @Query("select mpoCommitteeDescision from MpoCommitteeDescision mpoCommitteeDescision where mpoCommitteeDescision.user.login = ?#{principal.username}")
    List<MpoCommitteeDescision> findDescisionsByCurrentUser();

    @Query("select mpoCommitteeDescision.mpoApplication.id from MpoCommitteeDescision mpoCommitteeDescision where mpoCommitteeDescision.user.login = ?#{principal.username}")
    List<Long> findMpoIdsByCurrentUser();

}
