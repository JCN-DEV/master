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

import gov.step.app.domain.enumeration.AchievedCertificate;

/**
 * A SisEducationHistory.
 */
@Entity
@Table(name = "sis_education_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "siseducationhistory")
public class SisEducationHistory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "year_or_semester", nullable = false)
    private String yearOrSemester;

    @NotNull
    @Column(name = "roll_no", nullable = false)
    private String rollNo;

    @NotNull
    @Column(name = "major_or_dept", nullable = false)
    private String majorOrDept;

    @NotNull
    @Column(name = "division_or_gpa", nullable = false)
    private String divisionOrGpa;

    @NotNull
    @Column(name = "passing_year", nullable = false)
    private String passingYear;

    @NotNull
    //@Enumerated(EnumType.STRING)
    @Column(name = "achieved_certificate", nullable = false)
    //private AchievedCertificate achievedCertificate;
    private String achievedCertificate;

    @Column(name = "active_status")
    private Boolean activeStatus;

    @Column(name = "create_date", nullable = false)
    private LocalDate createDate;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "update_date", nullable = false)
    private LocalDate updateDate;

    @Column(name = "update_by")
    private Long updateBy;

    @ManyToOne
    @JoinColumn(name = "edu_level_id")
    private EduLevel eduLevel;

    @ManyToOne
    @JoinColumn(name = "edu_board_id")
    private EduBoard eduBoard;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getYearOrSemester() {
        return yearOrSemester;
    }

    public void setYearOrSemester(String yearOrSemester) {
        this.yearOrSemester = yearOrSemester;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getMajorOrDept() {
        return majorOrDept;
    }

    public void setMajorOrDept(String majorOrDept) {
        this.majorOrDept = majorOrDept;
    }

    public String getDivisionOrGpa() {
        return divisionOrGpa;
    }

    public void setDivisionOrGpa(String divisionOrGpa) {
        this.divisionOrGpa = divisionOrGpa;
    }

    public String getPassingYear() {
        return passingYear;
    }

    public void setPassingYear(String passingYear) {
        this.passingYear = passingYear;
    }

    /*public AchievedCertificate getAchievedCertificate() {
        return achievedCertificate;
    }

    public void setAchievedCertificate(AchievedCertificate achievedCertificate) {
        this.achievedCertificate = achievedCertificate;
    }*/

    public String getAchievedCertificate() {
        return achievedCertificate;
    }

    public void setAchievedCertificate(String achievedCertificate) {
        this.achievedCertificate = achievedCertificate;
    }

    public Boolean getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public EduLevel getEduLevel() {
        return eduLevel;
    }

    public void setEduLevel(EduLevel EduLevel) {
        this.eduLevel = EduLevel;
    }

    public EduBoard getEduBoard() {
        return eduBoard;
    }

    public void setEduBoard(EduBoard EduBoard) {
        this.eduBoard = EduBoard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SisEducationHistory sisEducationHistory = (SisEducationHistory) o;

        if ( ! Objects.equals(id, sisEducationHistory.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SisEducationHistory{" +
            "id=" + id +
            ", yearOrSemester='" + yearOrSemester + "'" +
            ", rollNo='" + rollNo + "'" +
            ", majorOrDept='" + majorOrDept + "'" +
            ", divisionOrGpa='" + divisionOrGpa + "'" +
            ", passingYear='" + passingYear + "'" +
            ", achievedCertificate='" + achievedCertificate + "'" +
            ", activeStatus='" + activeStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
