package gov.step.app.repository;

import gov.step.app.domain.DlBookInfo;

import gov.step.app.domain.DlBookIssue;
import gov.step.app.domain.DlContCatSet;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the DlBookInfo entity.
 */
public interface DlBookInfoRepository extends JpaRepository<DlBookInfo,Long> {
    @Query("select dlBookInfo from DlBookInfo dlBookInfo where dlBookInfo.bookId =:bookId AND dlBookInfo.pStatus=1 AND dlBookInfo.institute.id=:instId")
    DlBookInfo findBookInfoByBookId(@Param("bookId") String bookId,@Param("instId") Long instId);

    @Query("select dlBookInfo from DlBookInfo dlBookInfo where dlBookInfo.institute.user.login = ?#{principal.username}")
    List<DlBookInfo> findAllBookInfosByUserIsCurrentUser();

    @Query("select dlBookInfo from DlBookInfo dlBookInfo where dlBookInfo.dlContCatSet.id = :dlContCatSet AND dlBookInfo.dlContTypeSet.id = :dlContTypeSet ")
    List<DlBookInfo> findByallType(@Param("dlContCatSet") Long dlContCatSet, @Param("dlContTypeSet") Long dlContTypeSet);

    @Query("select dlBookInfo from DlBookInfo dlBookInfo where dlBookInfo.isbnNo = :isbnNo AND dlBookInfo.institute.id=:instId")
    Optional<DlBookInfo> validationforisbn(@Param("isbnNo") String isbnNo, @Param("instId") Long instId);


    @Query("select dlBookInfo from DlBookInfo dlBookInfo where dlBookInfo.bookId = :bookId AND dlBookInfo.institute.id=:instId")
    Optional<DlBookInfo> validationForBookId(@Param("bookId") String bookId, @Param("instId") Long instId);




     @Query("select dlBookInfo from DlBookInfo dlBookInfo where dlBookInfo.title = :title")
    List<DlBookInfo> getAuthorListByTitle(@Param("title") String title);


//     @Query("select dlBookInfo from DlBookInfo dlBookInfo where dlBookInfo.authorName = :authorName")
//    List<DlBookInfo> getEditionListByAuthorName(@Param("authorName") String authorName);
    @Query("select dlBookInfo from DlBookInfo dlBookInfo where dlBookInfo.dlContSCatSet.id =:id AND dlBookInfo.pStatus=1 AND dlBookInfo.institute.id=:instId")
    List<DlBookInfo> getAllBookInfoByScatAndInstitute(@Param("id") Long id,@Param("instId") Long instId);

}
