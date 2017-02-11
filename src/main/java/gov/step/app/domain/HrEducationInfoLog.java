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
 * A HrEducationInfoLog.
 */
@Entity
@Table(name = "hr_education_info_log")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "hreducationinfolog")
public class HrEducationInfoLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "exam_title")
    private String examTitle;

    @Column(name = "major_subject")
    private String majorSubject;

    @Column(name = "cert_sl_number")
    private String certSlNumber;

    @Column(name = "session_year")
    private String sessionYear;

    @Column(name = "roll_number")
    private String rollNumber;

    @Column(name = "institute_name")
    private String instituteName;

    @Column(name = "gpa_or_cgpa")
    private String gpaOrCgpa;

    @Column(name = "gpa_scale")
    private String gpaScale;

    @Column(name = "passing_year")
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
    @Column(name = "active_status")
    private Boolean activeStatus;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "log_status")
    private Long logStatus;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "action_date")
    private LocalDate actionDate;

    @Column(name = "action_by")
    private Long actionBy;

    @Column(name = "action_comments")
    private String actionComments;

    @ManyToOne
    @JoinColumn(name = "employee_info_id")
    private HrEmployeeInfo employeeInfo;

    @ManyToOne
    @JoinColumn(name = "education_level_id")
    private MiscTypeSetup educationLevel;

    @ManyToOne
    @JoinColumn(name = "education_board_id")
    private MiscTypeSetup educationBoard;


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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getLogStatus() {
        return logStatus;
    }

    public void setLogStatus(Long logStatus) {
        this.logStatus = logStatus;
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

    public LocalDate getActionDate() {
        return actionDate;
    }

    public void setActionDate(LocalDate actionDate) {
        this.actionDate = actionDate;
    }

    public Long getActionBy() {
        return actionBy;
    }

    public void setActionBy(Long actionBy) {
        this.actionBy = actionBy;
    }

    public String getActionComments() {
        return actionComments;
    }

    public void setActionComments(String actionComments) {
        this.actionComments = actionComments;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HrEducationInfoLog hrEducationInfoLog = (HrEducationInfoLog) o;
        return Objects.equals(id, hrEducationInfoLog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HrEducationInfoLog{" +
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
            ", parentId='" + parentId + "'" +
            ", logStatus='" + logStatus + "'" +
            ", createDate='" + createDate + "'" +
            ", createBy='" + createBy + "'" +
            ", actionDate='" + actionDate + "'" +
            ", actionBy='" + actionBy + "'" +
            ", actionComments='" + actionComments + "'" +
            '}';
    }
}
