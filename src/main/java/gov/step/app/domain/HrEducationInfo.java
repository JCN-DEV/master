package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A HrEducationInfo.
 */
@Entity
@Table(name = "hr_education_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "hreducationinfo")
public class HrEducationInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "exam_title", nullable = false)
    private String examTitle;

    @NotNull
    @Column(name = "major_subject", nullable = false)
    private String majorSubject;

    @Column(name = "cert_sl_number")
    private String certSlNumber;

    @NotNull
    @Column(name = "session_year", nullable = false)
    private String sessionYear;

    @Column(name = "roll_number")
    private String rollNumber;

    @Column(name = "institute_name")
    private String instituteName;

    @NotNull
    @Column(name = "gpa_or_cgpa", nullable = false)
    private String gpaOrCgpa;

    @Column(name = "gpa_scale")
    private String gpaScale;

    @NotNull
    @Column(name = "passing_year", nullable = false)
    private Long passingYear;

    @Lob
    @Column(name = "certificate_doc")
    private byte[] certificateDoc;

    @Column(name = "certificate_doc_content_type")
    private String certificateDocContentType;

    @Column(name = "certificate_doc_name")
    private String certificateDocName;

    @Lob
    @Column(name = "transcript_doc")
    private byte[] transcriptDoc;

    @Column(name = "transcript_doc_content_type")
    private String transcriptDocContentType;

    @Column(name = "transcript_doc_name")
    private String transcriptDocName;

    @NotNull
    @Column(name = "active_status", nullable = false)
    private Boolean activeStatus;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "update_date")
    private LocalDate updateDate;

    @Column(name = "update_by")
    private Long updateBy;

    @ManyToOne
    @JoinColumn(name = "employee_info_id")
    private HrEmployeeInfo employeeInfo;

    @ManyToOne
    @JoinColumn(name = "education_level_id")
    private MiscTypeSetup educationLevel;

    @ManyToOne
    @JoinColumn(name = "education_board_id")
    private MiscTypeSetup educationBoard;

    @Column(name = "log_id")
    private Long logId;

    @Column(name = "log_status")
    private Long logStatus;

    @Column(name = "log_comments")
    private String logComments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExamTitle() {
        return examTitle;
    }

    public void setExamTitle(String examTitle) {
        this.examTitle = examTitle;
    }

    public String getMajorSubject() {
        return majorSubject;
    }

    public void setMajorSubject(String majorSubject) {
        this.majorSubject = majorSubject;
    }

    public String getCertSlNumber() {
        return certSlNumber;
    }

    public void setCertSlNumber(String certSlNumber) {
        this.certSlNumber = certSlNumber;
    }

    public String getSessionYear() {
        return sessionYear;
    }

    public void setSessionYear(String sessionYear) {
        this.sessionYear = sessionYear;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public String getGpaOrCgpa() {
        return gpaOrCgpa;
    }

    public void setGpaOrCgpa(String gpaOrCgpa) {
        this.gpaOrCgpa = gpaOrCgpa;
    }

    public String getGpaScale() {
        return gpaScale;
    }

    public void setGpaScale(String gpaScale) {
        this.gpaScale = gpaScale;
    }

    public Long getPassingYear() {
        return passingYear;
    }

    public void setPassingYear(Long passingYear) {
        this.passingYear = passingYear;
    }

    public byte[] getCertificateDoc() {
        return certificateDoc;
    }

    public void setCertificateDoc(byte[] certificateDoc) {
        this.certificateDoc = certificateDoc;
    }

    public String getCertificateDocContentType() {
        return certificateDocContentType;
    }

    public void setCertificateDocContentType(String certificateDocContentType) {
        this.certificateDocContentType = certificateDocContentType;
    }

    public String getCertificateDocName() {
        return certificateDocName;
    }

    public void setCertificateDocName(String certificateDocName) {
        this.certificateDocName = certificateDocName;
    }

    public byte[] getTranscriptDoc() {
        return transcriptDoc;
    }

    public void setTranscriptDoc(byte[] transcriptDoc) {
        this.transcriptDoc = transcriptDoc;
    }

    public String getTranscriptDocContentType() {
        return transcriptDocContentType;
    }

    public void setTranscriptDocContentType(String transcriptDocContentType) {
        this.transcriptDocContentType = transcriptDocContentType;
    }

    public String getTranscriptDocName() {
        return transcriptDocName;
    }

    public void setTranscriptDocName(String transcriptDocName) {
        this.transcriptDocName = transcriptDocName;
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

    public HrEmployeeInfo getEmployeeInfo() {
        return employeeInfo;
    }

    public void setEmployeeInfo(HrEmployeeInfo HrEmployeeInfo) {
        this.employeeInfo = HrEmployeeInfo;
    }

    public MiscTypeSetup getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(MiscTypeSetup MiscTypeSetup) {
        this.educationLevel = MiscTypeSetup;
    }

    public MiscTypeSetup getEducationBoard() {
        return educationBoard;
    }

    public void setEducationBoard(MiscTypeSetup MiscTypeSetup) {
        this.educationBoard = MiscTypeSetup;
    }

    public Long getLogId() { return logId;}

    public void setLogId(Long logId) {this.logId = logId;}

    public Long getLogStatus() {return logStatus;}

    public void setLogStatus(Long logStatus) {this.logStatus = logStatus;}

    public String getLogComments() {return logComments;}

    public void setLogComments(String logComments) {this.logComments = logComments;}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HrEducationInfo hrEducationInfo = (HrEducationInfo) o;
        return Objects.equals(id, hrEducationInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HrEducationInfo{" +
            "id=" + id +
            ", examTitle='" + examTitle + "'" +
            ", majorSubject='" + majorSubject + "'" +
            ", certSlNumber='" + certSlNumber + "'" +
            ", sessionYear='" + sessionYear + "'" +
            ", rollNumber='" + rollNumber + "'" +
            ", instituteName='" + instituteName + "'" +
            ", gpaOrCgpa='" + gpaOrCgpa + "'" +
            ", gpaScale='" + gpaScale + "'" +
            ", passingYear='" + passingYear + "'" +
            ", certificateDoc='" + certificateDoc + "'" +
            ", certificateDocContentType='" + certificateDocContentType + "'" +
            ", certificateDocName='" + certificateDocName + "'" +
            ", transcriptDoc='" + transcriptDoc + "'" +
            ", transcriptDocContentType='" + transcriptDocContentType + "'" +
            ", transcriptDocName='" + transcriptDocName + "'" +
            ", activeStatus='" + activeStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", updateDate='" + updateDate + "'" +
            ", updateBy='" + updateBy + "'" +
            '}';
    }
}
