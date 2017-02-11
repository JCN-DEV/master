package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DlExistBookInfo.
 */
@Entity
@Table(name = "dl_exist_book_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "dlexistbookinfo")
public class DlExistBookInfo implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="dl_exist_book_info_seq")
    @SequenceGenerator(name="dl_exist_book_info_seq", sequenceName="dl_exist_book_info_seq")
    private Long id;


    @NotNull
    @Size(max = 100)
    @Column(name = "library_name", length = 100, nullable = false)
    private String libraryName;

    @NotNull
    @Size(max = 10)
    @Column(name = "self_no", length = 10, nullable = false)
    private String selfNo;

    @NotNull
    @Size(max = 10)
    @Column(name = "raw_no", length = 10, nullable = false)
    private String rawNo;

    @NotNull
    @Size(max = 120)
    @Column(name = "book_name", length = 120, nullable = false)
    private String bookName;

    @NotNull
    @Size(max = 45)
    @Column(name = "publisher_name", length = 45, nullable = false)
    private String publisherName;

    @NotNull
    @Size(max = 50)
    @Column(name = "copyright", length = 50, nullable = false)
    private String copyright;

    @NotNull
    @Size(max = 50)
    @Column(name = "isbn_no", length = 50, nullable = false)
    private String isbnNo;

    @NotNull
    @Column(name = "total_copies", nullable = false)
    private Integer totalCopies;

    @Size(max = 100)
    @Column(name = "upload_url", length = 100)
    private String uploadUrl;

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

    @ManyToOne
    @JoinColumn (name = "dl_book_type_id")
    private DlBookType dlBookType;

    @ManyToOne
    @JoinColumn (name = "dl_content_setup_id")
    private DlContentSetup dlContentSetup;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public String getSelfNo() {
        return selfNo;
    }

    public void setSelfNo(String selfNo) {
        this.selfNo = selfNo;
    }

    public String getRawNo() {
        return rawNo;
    }

    public void setRawNo(String rawNo) {
        this.rawNo = rawNo;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getIsbnNo() {
        return isbnNo;
    }

    public void setIsbnNo(String isbnNo) {
        this.isbnNo = isbnNo;
    }

    public Integer getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(Integer totalCopies) {
        this.totalCopies = totalCopies;
    }

    public String getUploadUrl() {
        return uploadUrl;
    }

    public void setUploadUrl(String uploadUrl) {
        this.uploadUrl = uploadUrl;
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

    public DlBookType getDlBookType() {
        return dlBookType;
    }

    public void setDlBookType(DlBookType dlBookType) {
        this.dlBookType = dlBookType;
    }

    public DlContentSetup getDlContentSetup() {
        return dlContentSetup;
    }

    public void setDlContentSetup(DlContentSetup dlContentSetup) {
        this.dlContentSetup = dlContentSetup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DlExistBookInfo dlExistBookInfo = (DlExistBookInfo) o;

        if ( ! Objects.equals(id, dlExistBookInfo.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DlExistBookInfo{" +
            "id=" + id +
            ", libraryName='" + libraryName + "'" +
            ", selfNo='" + selfNo + "'" +
            ", rawNo='" + rawNo + "'" +
            ", bookName='" + bookName + "'" +
            ", publisherName='" + publisherName + "'" +
            ", copyright='" + copyright + "'" +
            ", isbnNo='" + isbnNo + "'" +
            ", totalCopies='" + totalCopies + "'" +
            ", uploadUrl='" + uploadUrl + "'" +
            ", createdDate='" + createdDate + "'" +
            ", updatedDate='" + updatedDate + "'" +
            ", createdBy='" + createdBy + "'" +
            ", updatedBy='" + updatedBy + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
