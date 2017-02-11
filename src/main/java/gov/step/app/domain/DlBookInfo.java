package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DlBookInfo.
 */
@Entity
@Table(name = "dl_book_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "dlbookinfo")
public class DlBookInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title")
    private String title;




    @Column(name = "book_id")
    private String bookId;

    @Column(name = "edition")
    private String edition;

    @Column(name = "isbn_no")
    private String isbnNo;

    @Column(name = "book_img_name", nullable = true)
    private String bookImgName;

    @Lob
    @Column(name = "book_img", nullable = true)
    private byte[] bookImg;

    @Column(name = "book_img_content_type", nullable = true)
    private String bookImgContentType;

    @Column(name = "author_name")
    private String authorName;

    @Column(name = "copyright")
    private String copyright;

    @Column(name = "publisher_name")
    private String publisherName;

    @Column(name = "library_name")
    private String libraryName;

    @Column(name = "call_no")
    private String callNo;

    @Column(name = "total_copies")
    private String totalCopies;



    @Column(name = "compensation")
    private String compensation;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "updated_date")
    private LocalDate updatedDate;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @Column(name = "status")
    private Integer status;

    @Column(name = "p_status")
    private Boolean pStatus;

    @ManyToOne
    private DlContTypeSet dlContTypeSet;

    @ManyToOne
    @JoinColumn(name = "dl_source_set_up_id")
    private DlSourceSetUp dlSourceSetUp;

    @ManyToOne
    private DlContCatSet dlContCatSet;
    @OneToOne
    @JoinColumn(name = "dlBookRequisition_id")
    private DlBookRequisition dlBookRequisition;

    @ManyToOne
    private DlContSCatSet dlContSCatSet;
    @ManyToOne
    @JoinColumn(name = "institute_id")
    private Institute institute;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getIsbnNo() {
        return isbnNo;
    }

    public void setIsbnNo(String isbnNo) {
        this.isbnNo = isbnNo;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public String getCallNo() {
        return callNo;
    }

    public void setCallNo(String callNo) {
        this.callNo = callNo;
    }

    public String getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(String totalCopies) {
        this.totalCopies = totalCopies;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getpStatus() {
        return pStatus;
    }

    public void setpStatus(Boolean pStatus) {
        this.pStatus = pStatus;
    }

    public DlContTypeSet getDlContTypeSet() {
        return dlContTypeSet;
    }

    public void setDlContTypeSet(DlContTypeSet dlContTypeSet) {
        this.dlContTypeSet = dlContTypeSet;
    }

    public DlContCatSet getDlContCatSet() {
        return dlContCatSet;
    }

    public void setDlContCatSet(DlContCatSet dlContCatSet) {
        this.dlContCatSet = dlContCatSet;
    }

    public DlContSCatSet getDlContSCatSet() {
        return dlContSCatSet;
    }

    public void setDlContSCatSet(DlContSCatSet dlContSCatSet) {
        this.dlContSCatSet = dlContSCatSet;
    }

    public Institute getInstitute() {
        return institute;
    }

    public void setInstitute(Institute institute) {
        this.institute = institute;
    }

    public String getBookImgName() {
        return bookImgName;
    }

    public void setBookImgName(String bookImgName) {
        this.bookImgName = bookImgName;
    }

    public DlBookRequisition getDlBookRequisition() {
        return dlBookRequisition;
    }

    public void setDlBookRequisition(DlBookRequisition dlBookRequisition) {
        this.dlBookRequisition = dlBookRequisition;
    }

    public DlSourceSetUp getDlSourceSetUp() {
        return dlSourceSetUp;
    }

    public void setDlSourceSetUp(DlSourceSetUp dlSourceSetUp) {
        this.dlSourceSetUp = dlSourceSetUp;
    }

    public byte[] getBookImg() {
        return bookImg;
    }

    public void setBookImg(byte[] bookImg) {
        this.bookImg = bookImg;
    }

    public String getBookImgContentType() {
        return bookImgContentType;
    }

    public void setBookImgContentType(String bookImgContentType) {
        this.bookImgContentType = bookImgContentType;
    }
    public String getCompensation() {
        return compensation;
    }

    public void setCompensation(String compensation) {
        this.compensation = compensation;
    }
    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DlBookInfo dlBookInfo = (DlBookInfo) o;

        if ( ! Objects.equals(id, dlBookInfo.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DlBookInfo{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", edition='" + edition + "'" +
            ", isbnNo='" + isbnNo + "'" +
            ", authorName='" + authorName + "'" +
            ", copyright='" + copyright + "'" +
            ", publisherName='" + publisherName + "'" +
            ", libraryName='" + libraryName + "'" +
            ", callNo='" + callNo + "'" +
            ", totalCopies='" + totalCopies + "'" +
            ", purchaseDate='" + purchaseDate + "'" +
            ", createdDate='" + createdDate + "'" +
            ", updatedDate='" + updatedDate + "'" +
            ", createdBy='" + createdBy + "'" +
            ", updatedBy='" + updatedBy + "'" +
            ", status='" + status + "'" +
            ", pStatus='" + pStatus + "'" +
            '}';
    }
}
