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
 * A IisCurriculumInfo.
 */
@Entity
@Table(name = "iis_curriculum_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "iiscurriculuminfo")
public class IisCurriculumInfo implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="iis_curriculum_info_seq")
    @SequenceGenerator(name="iis_curriculum_info_seq", sequenceName="iis_curriculum_info_seq")
    private Long id;

    @Column(name = "first_date")
    private LocalDate firstDate;

    @Column(name = "last_date")
    private LocalDate lastDate;

    @Column(name = "mpo_enlisted")
    private Boolean mpoEnlisted;

    @Column(name = "rec_no")
    private String recNo;

    @Column(name = "mpo_date")
    private LocalDate mpoDate;

    @Column(name = "last_rec_no")
    private String lastRecNo;

    @Column(name = "last_mpo_date")
    private LocalDate lastMpoDate;

    //status 0=pending, 1=rejected, 2=approved
    @Column(name = "status")
    private Integer status;

    @Column(name = "cur_id")
    private String curId;

    @Column(name = "create_date")
    private LocalDate createDate;

    @ManyToOne
    @JoinColumn(name = "create_by")
    private User createBy;


    @Column(name = "update_date")
    private LocalDate updateDate;


    @ManyToOne
    @JoinColumn(name = "update_by")
    private User updateBy;


    @ManyToOne
    @JoinColumn(name = "institute_id")
    private Institute institute;

    @ManyToOne
    @JoinColumn(name = "cms_curriculum_id")
    private CmsCurriculum cmsCurriculum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFirstDate() {
        return firstDate;
    }

    public void setFirstDate(LocalDate firstDate) {
        this.firstDate = firstDate;
    }

    public LocalDate getLastDate() {
        return lastDate;
    }

    public void setLastDate(LocalDate lastDate) {
        this.lastDate = lastDate;
    }

    public Boolean getMpoEnlisted() {
        return mpoEnlisted;
    }

    public void setMpoEnlisted(Boolean mpoEnlisted) {
        this.mpoEnlisted = mpoEnlisted;
    }

    public String getRecNo() {
        return recNo;
    }

    public void setRecNo(String recNo) {
        this.recNo = recNo;
    }

    public String getCurId() {
        return curId;
    }

    public void setCurId(String curId) {
        this.curId = curId;
    }

    public LocalDate getMpoDate() {
        return mpoDate;
    }

    public void setMpoDate(LocalDate mpoDate) {
        this.mpoDate = mpoDate;
    }

    public Integer getStatus() {
        return status;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public User getCreateBy() {
        return createBy;
    }

    public void setCreateBy(User createBy) {
        this.createBy = createBy;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public User getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(User updateBy) {
        this.updateBy = updateBy;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Institute getInstitute() {
        return institute;
    }

    public void setInstitute(Institute institute) {
        this.institute = institute;
    }

    public CmsCurriculum getCmsCurriculum() {
        return cmsCurriculum;
    }

    public void setCmsCurriculum(CmsCurriculum cmsCurriculum) {
        this.cmsCurriculum = cmsCurriculum;
    }

    public String getLastRecNo() {
        return lastRecNo;
    }

    public void setLastRecNo(String lastRecNo) {
        this.lastRecNo = lastRecNo;
    }

    public LocalDate getLastMpoDate() {
        return lastMpoDate;
    }

    public void setLastMpoDate(LocalDate lastMpoDate) {
        this.lastMpoDate = lastMpoDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IisCurriculumInfo iisCurriculumInfo = (IisCurriculumInfo) o;

        if ( ! Objects.equals(id, iisCurriculumInfo.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "IisCurriculumInfo{" +
            "id=" + id +
            ", firstDate=" + firstDate +
            ", lastDate=" + lastDate +
            ", mpoEnlisted=" + mpoEnlisted +
            ", recNo='" + recNo + '\'' +
            ", mpoDate=" + mpoDate +
            ", lastRecNo='" + lastRecNo + '\'' +
            ", lastMpoDate=" + lastMpoDate +
            ", status=" + status +
            ", createDate=" + createDate +
            ", createBy=" + createBy +
            ", updateDate=" + updateDate +
            ", updateBy=" + updateBy +
            ", institute=" + institute +
            ", cmsCurriculum=" + cmsCurriculum +
            '}';
    }
}
