package gov.step.app.repository;

import gov.step.app.domain.DlBookInfo;
import gov.step.app.domain.DlBookIssue;

import gov.step.app.domain.DlBookReturn;
import gov.step.app.domain.DlContUpld;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the DlBookIssue entity.
 */
public interface DlBookIssueRepository extends JpaRepository<DlBookIssue,Long> {

    @Query("select dlBookIssue from DlBookIssue dlBookIssue where dlBookIssue.status=1 AND dlBookIssue.institute.user.login = ?#{principal.username}")
    List<DlBookIssue> findAllBookIssueByUserIsCurrentUser();

    @Query("select dlBookIssue from DlBookIssue dlBookIssue where dlBookIssue.status=2 AND dlBookIssue.sisStudentInfo.institute.user.login = ?#{principal.username} ")
    List<DlBookIssue> findAllbyUser();

    @Query("select dlBookIssue from DlBookIssue dlBookIssue where dlBookIssue.sisStudentInfo.id = :id")
    List<DlBookIssue> findStudentInfoByStudentId(@Param("id") Long id);

    @Query("select dlBookIssue from DlBookIssue dlBookIssue where dlBookIssue.sisStudentInfo.id = :id AND dlBookIssue.status=1")
    List<DlBookIssue> findIssueInfoByStuid(@Param("id") Long id);

    @Query("select dlBookIssue from DlBookIssue dlBookIssue where dlBookIssue.id = :id")
    DlBookIssue findIssueInfoByIssueId(@Param("id") Long id);


    @Query("select dlBookIssue from DlBookIssue dlBookIssue where dlBookIssue.status=1 AND dlBookIssue.sisStudentInfo.id=:sisId AND dlBookIssue.dlBookInfo.bookId=:BookInfoId AND dlBookIssue.dlBookEdition.id=:BookEdiId")
    DlBookIssue findAllBookIssue(@Param("sisId") Long sisId,@Param("BookInfoId") String BookInfoId,@Param("BookEdiId") Long BookEdiId);

    @Query("select dlBookIssue from DlBookIssue dlBookIssue where dlBookIssue.status=1 AND dlBookIssue.sisStudentInfo.id=:StudentId AND dlBookIssue.dlBookInfo.bookId =:bookId AND dlBookIssue.dlBookEdition.id=:BookEditionId")
    DlBookIssue findBookIssueForStudentRole(@Param("StudentId") Long StudentId,@Param("bookId") String bookId,@Param("BookEditionId") Long BookEditionId);
}
