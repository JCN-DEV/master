package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import gov.step.app.domain.enumeration.ResultType;

/**
 * A JpAcademicQualification.
 */

@Entity
@Table(name = "jp_academic_qualification")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "jpacademicqualification")
public class JpAcademicQualification implements Serializable {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="jp_ac_qua_seq")
    @SequenceGenerator(name="jp_ac_qua_seq", sequenceName="jp_ac_qua_seq")
    private Long id;

    //@NotNull
    @Size(max = 80)
    @Column(name = "education_level")
    private String educationLevel;

    //@NotNull
    @Size(max = 100)
    @Column(name = "degree_title")
    private String degreeTitle;

    //@NotNull
    @Size(max = 50)
    @Column(name = "major")
    private String major;

    //@NotNull
    @Size(max = 100)
    @Column(name = "institute")
    private String institute;

    //@NotNull
    //@Size(max = 100)
    @Column(name = "board")
    private String board;

    @Enumerated(EnumType.STRING)
    @Column(name = "resulttype")
    private ResultType resulttype;

    @NotNull
    @Size(max = 50)
    @Column(name = "result", length = 50, nullable = false)
    private String result;

    @NotNull
    @Column(name = "passingyear", nullable = false)
    private Integer passingyear;

    //@NotNull
    @Size(max = 30)
    @Column(name = "duration", length = 30, nullable = false)
    private String duration;

    @Column(name = "achivement")
    private String achivement;

    //groups : science, arts, commerce
    @Column(name = "exam_group")
    private String group;

    @Column(name = "from_university")
    private Boolean fromUniversity;

    @Column(name = "is_gpa_result")
    private Boolean isGpaResult;

    @Column(name = "university_name")
    private String universityName;

    @Column(name = "gpa_scale")
    private String gpaScale;

    @Column(name = "edu_session")
    private String session;


    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @ManyToOne
    @JoinColumn(name = "create_by")
    private User createBy;

    @ManyToOne
    @JoinColumn(name = "update_by")
    private User updateBy;


    @ManyToOne
    @JoinColumn(name = "jp_employee_id")
    private JpEmployee jpEmployee;

    @ManyToOne
    @JoinColumn(name = "edu_board_id")
    private EduBoard eduBoard;

    @ManyToOne
    @JoinColumn(name = "edu_level_id")
    private EduLevel eduLevel;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getDegreeTitle() {
        return degreeTitle;
    }

    public void setDegreeTitle(String degreeTitle) {
        this.degreeTitle = degreeTitle;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public ResultType getResulttype() {
        return resulttype;
    }

    public void setResulttype(ResultType resulttype) {
        this.resulttype = resulttype;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getPassingyear() {
        return passingyear;
    }

    public void setPassingyear(Integer passingyear) {
        this.passingyear = passingyear;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getAchivement() {
        return achivement;
    }

    public void setAchivement(String achivement) {
        this.achivement = achivement;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Boolean getFromUniversity() {
        return fromUniversity;
    }

    public void setFromUniversity(Boolean fromUniversity) {
        this.fromUniversity = fromUniversity;
    }

    public Boolean getIsGpaResult() {
        return isGpaResult;
    }

    public void setIsGpaResult(Boolean isGpaResult) {
        this.isGpaResult = isGpaResult;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getGpaScale() {
        return gpaScale;
    }

    public void setGpaScale(String gpaScale) {
        this.gpaScale = gpaScale;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
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

    public User getCreateBy() {
        return createBy;
    }

    public void setCreateBy(User createBy) {
        this.createBy = createBy;
    }

    public User getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(User updateBy) {
        this.updateBy = updateBy;
    }

    public JpEmployee getJpEmployee() {
        return jpEmployee;
    }

    public void setJpEmployee(JpEmployee JpEmployee) {
        this.jpEmployee = JpEmployee;
    }


    public EduBoard getEduBoard() {
        return eduBoard;
    }

    public void setEduBoard(EduBoard eduBoard) {
        this.eduBoard = eduBoard;
    }

    public EduLevel getEduLevel() {
        return eduLevel;
    }

    public void setEduLevel(EduLevel eduLevel) {
        this.eduLevel = eduLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JpAcademicQualification jpAcademicQualification = (JpAcademicQualification) o;

        if ( ! Objects.equals(id, jpAcademicQualification.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "JpAcademicQualification{" +
            "id=" + id +
            ", educationLevel='" + educationLevel + "'" +
            ", degreeTitle='" + degreeTitle + "'" +
            ", major='" + major + "'" +
            ", institute='" + institute + "'" +
            ", resulttype='" + resulttype + "'" +
            ", result='" + result + "'" +
            ", passingyear='" + passingyear + "'" +
            ", duration='" + duration + "'" +
            ", achivement='" + achivement + "'" +
            '}';
    }
}
