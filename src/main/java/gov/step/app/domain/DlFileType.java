package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DlFileType.
 */
@Entity
@Table(name = "dl_file_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "dlfiletype")
public class DlFileType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "limit_mb")
    private Integer limitMb;

    @Column(name = "file_img_name", nullable = true)
    private String fileImgName;

    @Lob
    @Column(name = "file_img", nullable = true)
    private byte[] fileImg;

    @Column(name = "file_img_content_type", nullable = true)
    private String fileImgContentType;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Integer getLimitMb() {
        return limitMb;
    }

    public void setLimitMb(Integer limitMb) {
        this.limitMb = limitMb;
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

    public String getFileImgName() {
        return fileImgName;
    }

    public void setFileImgName(String fileImgName) {
        this.fileImgName = fileImgName;
    }

    public byte[] getFileImg() {
        return fileImg;
    }

    public void setFileImg(byte[] fileImg) {
        this.fileImg = fileImg;
    }

    public String getFileImgContentType() {
        return fileImgContentType;
    }

    public void setFileImgContentType(String fileImgContentType) {
        this.fileImgContentType = fileImgContentType;
    }

    public Boolean getpStatus() {
        return pStatus;
    }

    public void setpStatus(Boolean pStatus) {
        this.pStatus = pStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DlFileType dlFileType = (DlFileType) o;

        if ( ! Objects.equals(id, dlFileType.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DlFileType{" +
            "id=" + id +
            ", fileType='" + fileType + "'" +
            ", limitMb='" + limitMb + "'" +
            ", createdDate='" + createdDate + "'" +
            ", updatedDate='" + updatedDate + "'" +
            ", createdBy='" + createdBy + "'" +
            ", updatedBy='" + updatedBy + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
