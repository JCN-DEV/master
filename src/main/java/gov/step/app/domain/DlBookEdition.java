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
 * A DlBookEdition.
 */
@Entity
@Table(name = "dl_book_edition")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "dlbookedition")
public class DlBookEdition implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "edition")
    private String edition;

    @Column(name = "total_copies")
    private String totalCopies;


    @Column(name = "total_book_copies")
    private String totalBookCopies;

    @Column(name = "compensation")
    private String compensation;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "create_by")
    private Integer createBy;

    @Column(name = "update_by")
    private Integer updateBy;

    @Column(name = "status")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "dl_book_info_id")
    private DlBookInfo dlBookInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(String totalCopies) {
        this.totalCopies = totalCopies;
    }

    public String getCompensation() {
        return compensation;
    }

    public void setCompensation(String compensation) {
        this.compensation = compensation;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public DlBookInfo getDlBookInfo() {
        return dlBookInfo;
    }

    public void setDlBookInfo(DlBookInfo dlBookInfo) {
        this.dlBookInfo = dlBookInfo;
    }

    public String getTotalBookCopies() {
        return totalBookCopies;
    }

    public void setTotalBookCopies(String totalBookCopies) {
        this.totalBookCopies = totalBookCopies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DlBookEdition dlBookEdition = (DlBookEdition) o;
        return Objects.equals(id, dlBookEdition.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DlBookEdition{" +
            "id=" + id +
            ", edition='" + edition + "'" +
            ", totalCopies='" + totalCopies + "'" +
            ", compensation='" + compensation + "'" +
            ", createDate='" + createDate + "'" +
            ", updateDate='" + updateDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateBy='" + updateBy + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
