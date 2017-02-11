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
 * A DlBookRequisition.
 */
@Entity
@Table(name = "dl_book_requisition")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "dlbookrequisition")
public class DlBookRequisition implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "edition")
    private String edition;

    @Column(name = "author_name")
    private String authorName;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "update_by")
    private Long updateBy;

    @Column(name = "status")
    private Boolean status;



    @ManyToOne
    @JoinColumn (name = "dl_cont_type_set")
    private DlContTypeSet dlContTypeSet;

    @ManyToOne
    @JoinColumn (name = "dl_cont_cat_set")
    private DlContCatSet dlContCatSet;

    @ManyToOne
    @JoinColumn (name = "dl_cont_scat_set")
    private DlContSCatSet dlContSCatSet;


    @ManyToOne
    @JoinColumn(name = "sis_student_info")
    private SisStudentInfo sisStudentInfo;

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

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
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

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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

    public SisStudentInfo getSisStudentInfo() {
        return sisStudentInfo;
    }

    public void setSisStudentInfo(SisStudentInfo sisStudentInfo) {
        this.sisStudentInfo = sisStudentInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DlBookRequisition dlBookRequisition = (DlBookRequisition) o;

        if ( ! Objects.equals(id, dlBookRequisition.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DlBookRequisition{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", edition='" + edition + "'" +
            ", authorName='" + authorName + "'" +
            ", createDate='" + createDate + "'" +
            ", updateDate='" + updateDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateBy='" + updateBy + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
