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
 * A IisCourseInfo.
 */
@Entity
@Table(name = "iis_course_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "iiscourseinfo")
public class IisCourseInfo implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="iis_course_info_seq")
    @SequenceGenerator(name="iis_course_info_seq", sequenceName="iis_course_info_seq")
    private Long id;

    @Column(name = "per_date_edu")
    private LocalDate perDateEdu;

    @Column(name = "per_date_bteb")
    private LocalDate perDateBteb;

    @Column(name = "mpo_enlisted")
    private Boolean mpoEnlisted;

    @Column(name = "date_mpo")
    private LocalDate dateMpo;

    @Column(name = "seat_no")
    private Integer seatNo;

    @Column(name = "memo_no")
    private String memoNo;

    @Column(name = "shift")
    private String shift;

    @Column(name = "vacancy_role_applied")
    private Boolean vacancyRoleApplied;

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
    @JoinColumn(name = "iis_curriculum_info_id")
    private IisCurriculumInfo iisCurriculumInfo;

    @ManyToOne
    @JoinColumn(name = "cms_trade_id")
    private CmsTrade cmsTrade;

    @ManyToOne
    @JoinColumn(name = "cms_subject_id")
    private CmsSubject cmsSubject;

    @ManyToOne
    @JoinColumn(name = "institute_id")
    private Institute institute;

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

    public LocalDate getDateMpo() {
        return dateMpo;
    }

    public void setDateMpo(LocalDate dateMpo) {
        this.dateMpo = dateMpo;
    }

    public Boolean getVacancyRoleApplied() {
        return vacancyRoleApplied;
    }

    public void setVacancyRoleApplied(Boolean vacancyRoleApplied) {
        this.vacancyRoleApplied = vacancyRoleApplied;
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

    public String getMemoNo() {
        return memoNo;
    }

    public void setMemoNo(String memoNo) {
        this.memoNo = memoNo;
    }

    public void setShift(String shift) {
        this.shift = shift;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IisCourseInfo iisCourseInfo = (IisCourseInfo) o;

        if ( ! Objects.equals(id, iisCourseInfo.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "IisCourseInfo{" +
            "id=" + id +
            ", perDateEdu='" + perDateEdu + "'" +
            ", perDateBteb='" + perDateBteb + "'" +
            ", mpoEnlisted='" + mpoEnlisted + "'" +
            ", dateMpo='" + dateMpo + "'" +
            ", seatNo='" + seatNo + "'" +
            ", shift='" + shift + "'" +
            '}';
    }
}
