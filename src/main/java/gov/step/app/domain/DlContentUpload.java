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

import gov.step.app.domain.enumeration.UserType;

/**
 * A DlContentUpload.
 */
@Entity
@Table(name = "dl_content_upload")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "dlcontentupload")
public class DlContentUpload implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="dl_content_upload_seq")
    @SequenceGenerator(name="dl_content_upload_seq", sequenceName="dl_content_upload_seq")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    private UserType userType;

    @NotNull
    @Size(max = 150)
    @Column(name = "content_name", length = 150, nullable = false)
    private String contentName;

    @NotNull
    @Size(max = 55)
    @Column(name = "edition", length = 55, nullable = false)
    private String edition;

    @NotNull
    @Size(max = 65)
    @Column(name = "author_name", length = 65, nullable = false)
    private String authorName;

    @NotNull
    @Size(max = 60)
    @Column(name = "isbn_no", length = 60, nullable = false)
    private String isbnNo;

    @NotNull
    @Size(max = 60)
    @Column(name = "publisher_name", length = 60, nullable = false)
    private String publisherName;

    @NotNull
    @Size(max = 60)
    @Column(name = "copyright", length = 60, nullable = false)
    private String copyright;

    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    @Column(name = "updated_date", nullable = false)
    private LocalDate updatedDate;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @Column(name = "status")
    private Integer status;

    @ManyToOne
    @JoinColumn (name = "dl_content_setup_id")
    private DlContentSetup dlContentSetup;

    @ManyToOne
    @JoinColumn (name = "dl_book_type_id")
    private DlBookType dlBookType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getIsbnNo() {
        return isbnNo;
    }

    public void setIsbnNo(String isbnNo) {
        this.isbnNo = isbnNo;
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

    public DlContentSetup getDlContentSetup() {
        return dlContentSetup;
    }

    public void setDlContentSetup(DlContentSetup dlContentSetup) {
        this.dlContentSetup = dlContentSetup;
    }

    public DlBookType getDlBookType() {
        return dlBookType;
    }

    public void setDlBookType(DlBookType dlBookType) {
        this.dlBookType = dlBookType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DlContentUpload dlContentUpload = (DlContentUpload) o;

        if ( ! Objects.equals(id, dlContentUpload.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DlContentUpload{" +
            "id=" + id +
            ", userType='" + userType + "'" +
            ", contentName='" + contentName + "'" +
            ", edition='" + edition + "'" +
            ", authorName='" + authorName + "'" +
            ", isbnNo='" + isbnNo + "'" +
            ", publisherName='" + publisherName + "'" +
            ", copyright='" + copyright + "'" +
            ", createdDate='" + createdDate + "'" +
            ", updatedDate='" + updatedDate + "'" +
            ", createdBy='" + createdBy + "'" +
            ", updatedBy='" + updatedBy + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
