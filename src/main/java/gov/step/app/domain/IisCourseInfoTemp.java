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
 * A IisCourseInfoTemp.
 */
@Entity
@Table(name = "iis_course_info_temp")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "iiscourseinfotemp")
public class IisCourseInfoTemp implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "per_date_edu")
    private LocalDate perDateEdu;

    @Column(name = "per_date_bteb")
    private LocalDate perDateBteb;

    @Column(name = "mpo_enlisted")
    private Boolean mpoEnlisted;

    @Column(name = "date_mpo")
    private String dateMpo;

    @Column(name = "seat_no")
    private Integer seatNo;

    @Column(name = "shift")
    private String shift;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "memo_no")
    private String memoNo;

    //status 0=pending, 1=rejected, 2=approved
    @Column(name = "status")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "iiscurriculuminfo_id")
    private IisCurriculumInfo iisCurriculumInfo;

    @ManyToOne
    @JoinColumn(name = "cmstrade_id")
    private CmsTrade cmsTrade;

    @ManyToOne
    @JoinColumn(name = "cmssubject_id")
    private CmsSubject cmsSubject;

    @ManyToOne
    @JoinColumn(name = "institute_id")
    private Institute institute;

    @ManyToOne
    @JoinColumn(name = "create_by")
    private User createBy;

    @ManyToOne
    @JoinColumn(name = "update_by")
    private User updateBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getPerDateEdu() {
        return perDateEdu;
    }

    public void setPerDateEdu(LocalDate perDateEdu) {
        this.perDateEdu = perDateEdu;
    }

    public LocalDate getPerDateBteb() {
        return perDateBteb;
    }

    public void setPerDateBteb(LocalDate perDateBteb) {
        this.perDateBteb = perDateBteb;
    }

    public Boolean getMpoEnlisted() {
        return mpoEnlisted;
    }

    public void setMpoEnlisted(Boolean mpoEnlisted) {
        this.mpoEnlisted = mpoEnlisted;
    }

    public String getDateMpo() {
        return dateMpo;
    }

    public void setDateMpo(String dateMpo) {
        this.dateMpo = dateMpo;
    }

    public Integer getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(Integer seatNo) {
        this.seatNo = seatNo;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }


    public String getMemoNo() {
        return memoNo;
    }

    public void setMemoNo(String memoNo) {
        this.memoNo = memoNo;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public IisCurriculumInfo getIisCurriculumInfo() {
        return iisCurriculumInfo;
    }

    public void setIisCurriculumInfo(IisCurriculumInfo iisCurriculumInfo) {
        this.iisCurriculumInfo = iisCurriculumInfo;
    }

    public CmsTrade getCmsTrade() {
        return cmsTrade;
    }

    public void setCmsTrade(CmsTrade cmsTrade) {
        this.cmsTrade = cmsTrade;
    }

    public CmsSubject getCmsSubject() {
        return cmsSubject;
    }

    public void setCmsSubject(CmsSubject cmsSubject) {
        this.cmsSubject = cmsSubject;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IisCourseInfoTemp iisCourseInfoTemp = (IisCourseInfoTemp) o;

        if ( ! Objects.equals(id, iisCourseInfoTemp.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "IisCourseInfoTemp{" +
            "id=" + id +
            ", perDateEdu='" + perDateEdu + "'" +
            ", perDateBteb='" + perDateBteb + "'" +
            ", mpoEnlisted='" + mpoEnlisted + "'" +
            ", dateMpo='" + dateMpo + "'" +
            ", seatNo='" + seatNo + "'" +
            ", shift='" + shift + "'" +
            ", createDate='" + createDate + "'" +
            ", updateDate='" + updateDate + "'" +
            '}';
    }
}
