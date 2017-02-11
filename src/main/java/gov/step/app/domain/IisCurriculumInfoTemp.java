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
 * A IisCurriculumInfoTemp.
 */
@Entity
@Table(name = "iis_curriculum_info_temp")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "iiscurriculuminfotemp")
public class IisCurriculumInfoTemp implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "first_date")
    private LocalDate firstDate;

    @Column(name = "last_date")
    private LocalDate lastDate;

    @Column(name = "mpo_enlisted")
    private Boolean mpoEnlisted;

    @Column(name = "rec_no")
    private String recNo;

    @Column(name = "cur_id")
    private String curId;


    @Column(name = "last_rec_no")
    private String lastRecNo;

    @Column(name = "last_mpo_date")
    private LocalDate lastMpoDate;

    //status 0=pending, 1=rejected, 2=approved
    @Column(name = "status")
    private Integer status;

    @Column(name = "mpo_date")
    private LocalDate mpoDate;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @ManyToOne
    private CmsCurriculum cmsCurriculum;

    @ManyToOne
    private Institute institute;

    @ManyToOne
    private User createBy;

    @ManyToOne
    private User updateBy;

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

    public CmsCurriculum getCmsCurriculum() {
        return cmsCurriculum;
    }

    public void setCmsCurriculum(CmsCurriculum cmsCurriculum) {
        this.cmsCurriculum = cmsCurriculum;
    }

    public Institute getInstitute() {
        return institute;
    }

    public void setInstitute(Institute institute) {
        this.institute = institute;
    }

    public User getCreateBy() {
        return createBy;
    }

    public void setCreateBy(User user) {
        this.createBy = user;
    }

    public User getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(User user) {
        this.updateBy = user;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

        IisCurriculumInfoTemp iisCurriculumInfoTemp = (IisCurriculumInfoTemp) o;

        if ( ! Objects.equals(id, iisCurriculumInfoTemp.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "IisCurriculumInfoTemp{" +
            "id=" + id +
            ", firstDate=" + firstDate +
            ", lastDate=" + lastDate +
            ", mpoEnlisted=" + mpoEnlisted +
            ", recNo='" + recNo + '\'' +
            ", lastRecNo='" + lastRecNo + '\'' +
            ", lastMpoDate=" + lastMpoDate +
            ", status=" + status +
            ", mpoDate=" + mpoDate +
            ", createDate=" + createDate +
            ", updateDate=" + updateDate +
            ", cmsCurriculum=" + cmsCurriculum +
            ", institute=" + institute +
            ", createBy=" + createBy +
            ", updateBy=" + updateBy +
            '}';
    }
}
