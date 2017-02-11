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
 * A InstEmpEduQuali.
 */
@Entity
@Table(name = "inst_emp_edu_quali")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "instempeduquali")
public class InstEmpEduQuali implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "certificate_name")
    private String certificateName;

    @Column(name = "board")
    private String board;

    @Column(name = "edu_session")
    private String session;

    @Column(name = "semester")
    private String semester;

    @Column(name = "roll_no")
    private String rollNo;

    @Column(name = "passing_year")
    private Integer passingYear;

    @Column(name = "cgpa")
    private String cgpa;

    //groups : science, arts, commerce
    @Column(name = "exam_group")
    private String group;

    @Lob
    @Column(name = "certificate_copy")
    private byte[] certificateCopy;

    @Column(name = "CERT_CPY_NAME")
    private String certificateCopyName;

    @Column(name = "CERT_CPY_CNT_TYPE")
    private String certificateCopyContentType;
    @Column(name = "status")
    private Integer status;

    @Column(name = "group_subject")
    private String groupSubject;


    @Column(name = "duration")
    private String duration;

    @Column(name = "result_publish_date")
    private LocalDate resultPublishDate;

    @Column(name = "from_university")
    private Boolean fromUniversity;

    @Column(name = "is_gpa_result")
    private Boolean isGpaResult;

    @Column(name = "university_name")
    private String universityName;

    @Column(name = "gpa_scale")
    private String gpaScale;

    @Column(name = "create_date")
    private LocalDate dateCrated;

    @Column(name = "update_date")
    private LocalDate dateModified;

    @ManyToOne
    @JoinColumn(name = "create_by")
    private User createBy;

    @ManyToOne
    @JoinColumn(name = "update_by")
    private User updateBy;


    @ManyToOne
    @JoinColumn(name = "edu_board_id")
    private EduBoard eduBoard;

    @ManyToOne
    @JoinColumn(name = "edu_level_id")
    private EduLevel eduLevel ;

    @ManyToOne
    @JoinColumn(name = "inst_employee_id")
    private InstEmployee instEmployee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCertificateName() {
        return certificateName;
    }

    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public Integer getPassingYear() {
        return passingYear;
    }

    public void setPassingYear(Integer passingYear) {
        this.passingYear = passingYear;
    }

    public String getCgpa() {
        return cgpa;
    }

    public void setCgpa(String cgpa) {
        this.cgpa = cgpa;
    }

    public byte[] getCertificateCopy() {
        return certificateCopy;
    }

    public void setCertificateCopy(byte[] certificateCopy) {
        this.certificateCopy = certificateCopy;
    }

    public String getCertificateCopyName() {
        return certificateCopyName;
    }

    public void setCertificateCopyName(String certificateCopyName) {
        this.certificateCopyName = certificateCopyName;
    }

    public String getCertificateCopyContentType() {
        return certificateCopyContentType;
    }

    public void setCertificateCopyContentType(String certificateCopyContentType) {
        this.certificateCopyContentType = certificateCopyContentType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getGroupSubject() {
        return groupSubject;
    }

    public void setGroupSubject(String groupSubject) {
        this.groupSubject = groupSubject;
    }

    public LocalDate getResultPublishDate() {
        return resultPublishDate;
    }

    public void setResultPublishDate(LocalDate resultPublishDate) {
        this.resultPublishDate = resultPublishDate;
    }

    public Boolean getFromUniversity() {
        return fromUniversity;
    }

    public void setFromUniversity(Boolean fromUniversity) {
        this.fromUniversity = fromUniversity;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public Boolean getIsGpaResult() {
        return isGpaResult;
    }

    public void setIsGpaResult(Boolean isGpaResult) {
        this.isGpaResult = isGpaResult;
    }

    public String getGpaScale() {
        return gpaScale;
    }

    public void setGpaScale(String gpaScale) {
        this.gpaScale = gpaScale;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public LocalDate getDateCrated() {
        return dateCrated;
    }

    public void setDateCrated(LocalDate dateCrated) {
        this.dateCrated = dateCrated;
    }

    public LocalDate getDateModified() {
        return dateModified;
    }

    public void setDateModified(LocalDate dateModified) {
        this.dateModified = dateModified;
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

    public InstEmployee getInstEmployee() {
        return instEmployee;
    }

    public void setInstEmployee(InstEmployee instEmployee) {
        this.instEmployee = instEmployee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InstEmpEduQuali instEmpEduQuali = (InstEmpEduQuali) o;

        if ( ! Objects.equals(id, instEmpEduQuali.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InstEmpEduQuali{" +
            "id=" + id +
            ", certificateName='" + certificateName + "'" +
            ", board='" + board + "'" +
            ", session='" + session + "'" +
            ", semester='" + semester + "'" +
            ", rollNo='" + rollNo + "'" +
            ", passingYear='" + passingYear + "'" +
            ", cgpa='" + cgpa + "'" +
            ", certificateCopy='" + certificateCopy + "'" +
            ", certificateCopyContentType='" + certificateCopyContentType + "'" +
            ", status='" + status + "'" +
            ", groupSubject='" + groupSubject + "'" +
            ", resultPublishDate='" + resultPublishDate + "'" +
            '}';
    }
}
