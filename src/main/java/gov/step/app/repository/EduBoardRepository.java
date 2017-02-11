package gov.step.app.repository;

import gov.step.app.domain.EduBoard;

import gov.step.app.domain.OrganizationCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the EduBoard entity.
 */
public interface EduBoardRepository extends JpaRepository<EduBoard,Long> {

    public Page<EduBoard> findAllByOrderByIdDesc(Pageable pageable);

    @Query("SELECT eduBoard from EduBoard eduBoard where lower(eduBoard.name) = :name")
    EduBoard findOneByName(@Param("name") String name);

    @Query("SELECT eduBoard from EduBoard eduBoard where lower(eduBoard.boardType) = :boardType and eduBoard.status = true")
    List<EduBoard> findAllActiveByType(@Param("boardType") String boardType);
}
