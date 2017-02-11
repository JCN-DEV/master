package gov.step.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gov.step.app.domain.enumeration.Gender;
import gov.step.app.domain.enumeration.EmployeeCategory;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Employee.
 */
@Entity
@Table(name = "employee")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "employee")
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @NotNull
    @Size(max = 50)
    @Column(name = "name_english", length = 50, nullable = false)
    private String nameEnglish;

    @NotNull
    @Size(max = 50)
    @Column(name = "father", length = 50, nullable = false)
    private String father;

    @NotNull
    @Size(max = 50)
    @Column(name = "mother", length = 50, nullable = false)
    private String mother;

    @NotNull
    @Column(name = "present_address", nullable = false)
    private String presentAddress;

    @NotNull
    @Column(name = "permanent_address", nullable = false)
    private String permanentAddress;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @NotNull
    @Column(name = "dob", nullable = false)
    private LocalDate dob;

    @NotNull
    @Size(max = 50)
    @Column(name = "zip_code", length = 50, nullable = false)
    private String zipCode;

    @Size(max = 255)
    @Column(name = "reg_cert_sub", length = 255, nullable = false)
    private String registrationCertificateSubject;

    @Column(name = "reg_exam_year", nullable = false)
    private Integer registrationExamYear;

    @Size(max = 60)
    @Column(name = "reg_cert_no", length = 60, nullable = false)
    private String registrationCetificateNo;

    @Size(max = 20)
    @Column(name = "index_no", length = 20)
    private String indexNo;

    @Size(max = 50)
    @Column(name = "bank_name", length = 50)
    private String bankName;

    @Size(max = 50)
    @Column(name = "bank_branch", length = 50)
    private String bankBranch;

    @Size(max = 50)
    @Column(name = "bank_account_no", length = 50)
    private String bankAccountNo;

    @Size(max = 100)
    @Column(name = "designation", length = 100, nullable = false)
    private String designation;

    @Size(max = 100)
    @Column(name = "subject", length = 100, nullable = false)
    private String subject;

    @Size(max = 30)
    @Column(name = "salary_scale", length = 30, nullable = false)
    private String salaryScale;

    @Size(max = 20)
    @Column(name = "salary_code", length = 20, nullable = false)
    private String salaryCode;

    @Column(name = "mnth_slry_govt_provide", precision = 10, scale = 2, nullable = false)
    private BigDecimal monthlySalaryGovtProvided;

    @Column(name = "mnth_slry_inst_provide", precision = 10, scale = 2, nullable = false)
    private BigDecimal monthlySalaryInstituteProvided;

    @Column(name = "gb_resol_recv_date", nullable = false)
    private LocalDate gbResolutionReceiveDate;

    @Size(max = 30)
    @Column(name = "gb_resol_agenda_no", length = 30, nullable = false)
    private String gbResolutionAgendaNo;

    @Size(max = 50)
    @Column(name = "circular_paper_name", length = 50, nullable = false)
    private String circularPaperName;

    @Column(name = "circular_published_date", nullable = false)
    private LocalDate circularPublishedDate;

    @Column(name = "recruit_exam_rcev_date", nullable = false)
    private LocalDate recruitExamReceiveDate;

    @Size(max = 50)
    @Column(name = "dg_represent_name", length = 50, nullable = false)
    private String dgRepresentativeName;

    @Size(max = 50)
    @Column(name = "dg_represent_designa", length = 50, nullable = false)
    private String dgRepresentativeDesignation;

    @Size(max = 60)
    @Column(name = "dg_represent_addr", length = 60, nullable = false)
    private String dgRepresentativeAddress;

    @Size(max = 50)
    @Column(name = "board_represent_name", length = 50, nullable = false)
    private String boardRepresentativeName;

    @Size(max = 50)
    @Column(name = "bord_represent_designa", length = 50, nullable = false)
    private String boardRepresentativeDesignation;

    @Size(max = 127)
    @Column(name = "bord_represent_addr", length = 127, nullable = false)
    private String boardRepresentativeAddress;

    @Column(name = "recrut_aprv_gbresol_date", nullable = false)
    private LocalDate recruitApproveGBResolutionDate;

    @Size(max = 50)
    @Column(name = "recruit_permit_agenda_no", length = 50)
    private String recruitPermitAgendaNo;

    @Column(name = "recruitment_date", nullable = false)
    private LocalDate recruitmentDate;

    @Column(name = "present_inst_join_date", nullable = false)
    private LocalDate presentInstituteJoinDate;

    @Column(name = "present_post_join_date", nullable = false)
    private LocalDate presentPostJoinDate;

    @Column(name = "time_scale_gbresol_date", nullable = false)
    private LocalDate timeScaleGBResolutionDate;

    @Column(name = "total_job_duration", nullable = false)
    private Integer totalJobDuration;

    @Column(name = "curnt_posi_job_dura", nullable = false)
    private Integer currentPositionJobDuration;


    @Lob
    @Column(name = "cv", nullable = true)
    private byte[] cv;


    @Column(name = "cv_content_type", nullable = true)
    private String cvContentType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private EmployeeCategory category;

    @ManyToOne
    private User manager;

    @ManyToOne
    private Institute institute;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "employee_pay_scale",
        joinColumns = @JoinColumn(name = "employees_id", referencedColumnName = "ID"),
        inverseJoinColumns = @JoinColumn(name = "pay_scales_id", referencedColumnName = "ID"))
    private Set<PayScale> payScales = new HashSet<>();

    @OneToOne
    private User user;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ApplicantEducation> applicantEducations = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Training> trainings = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Skill> skills = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Reference> references = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Lang> langs = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameEnglish() {
        return nameEnglish;
    }

    public void setNameEnglish(String nameEnglish) {
        this.nameEnglish = nameEnglish;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getMother() {
        return mother;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public String getPresentAddress() {
        return presentAddress;
    }

    public void setPresentAddress(String presentAddress) {
        this.presentAddress = presentAddress;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public byte[] getCv() {
        return cv;
    }

    public void setCv(byte[] cv) {
        this.cv = cv;
    }

    public String getCvContentType() {
        return cvContentType;
    }

    public void setCvContentType(String cvContentType) {
        this.cvContentType = cvContentType;
    }

    public String getRegistrationCertificateSubject() {
        return registrationCertificateSubject;
    }

    public void setRegistrationCertificateSubject(String registrationCertificateSubject) {
        this.registrationCertificateSubject = registrationCertificateSubject;
    }

    public Integer getRegistrationExamYear() {
        return registrationExamYear;
    }

    public void setRegistrationExamYear(Integer registrationExamYear) {
        this.registrationExamYear = registrationExamYear;
    }

    public String getRegistrationCetificateNo() {
        return registrationCetificateNo;
    }

    public void setRegistrationCetificateNo(String registrationCetificateNo) {
        this.registrationCetificateNo = registrationCetificateNo;
    }

    public String getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(String indexNo) {
        this.indexNo = indexNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSalaryScale() {
        return salaryScale;
    }

    public void setSalaryScale(String salaryScale) {
        this.salaryScale = salaryScale;
    }

    public String getSalaryCode() {
        return salaryCode;
    }

    public void setSalaryCode(String salaryCode) {
        this.salaryCode = salaryCode;
    }

    public BigDecimal getMonthlySalaryGovtProvided() {
        return monthlySalaryGovtProvided;
    }

    public void setMonthlySalaryGovtProvided(BigDecimal monthlySalaryGovtProvided) {
        this.monthlySalaryGovtProvided = monthlySalaryGovtProvided;
    }

    public BigDecimal getMonthlySalaryInstituteProvided() {
        return monthlySalaryInstituteProvided;
    }

    public void setMonthlySalaryInstituteProvided(BigDecimal monthlySalaryInstituteProvided) {
        this.monthlySalaryInstituteProvided = monthlySalaryInstituteProvided;
    }

    public LocalDate getGbResolutionReceiveDate() {
        return gbResolutionReceiveDate;
    }

    public void setGbResolutionReceiveDate(LocalDate gbResolutionReceiveDate) {
        this.gbResolutionReceiveDate = gbResolutionReceiveDate;
    }

    public String getGbResolutionAgendaNo() {
        return gbResolutionAgendaNo;
    }

    public void setGbResolutionAgendaNo(String gbResolutionAgendaNo) {
        this.gbResolutionAgendaNo = gbResolutionAgendaNo;
    }

    public String getCircularPaperName() {
        return circularPaperName;
    }

    public void setCircularPaperName(String circularPaperName) {
        this.circularPaperName = circularPaperName;
    }

    public LocalDate getCircularPublishedDate() {
        return circularPublishedDate;
    }

    public void setCircularPublishedDate(LocalDate circularPublishedDate) {
        this.circularPublishedDate = circularPublishedDate;
    }

    public LocalDate getRecruitExamReceiveDate() {
        return recruitExamReceiveDate;
    }

    public void setRecruitExamReceiveDate(LocalDate recruitExamReceiveDate) {
        this.recruitExamReceiveDate = recruitExamReceiveDate;
    }

    public String getDgRepresentativeName() {
        return dgRepresentativeName;
    }

    public void setDgRepresentativeName(String dgRepresentativeName) {
        this.dgRepresentativeName = dgRepresentativeName;
    }

    public String getDgRepresentativeDesignation() {
        return dgRepresentativeDesignation;
    }

    public void setDgRepresentativeDesignation(String dgRepresentativeDesignation) {
        this.dgRepresentativeDesignation = dgRepresentativeDesignation;
    }

    public String getDgRepresentativeAddress() {
        return dgRepresentativeAddress;
    }

    public void setDgRepresentativeAddress(String dgRepresentativeAddress) {
        this.dgRepresentativeAddress = dgRepresentativeAddress;
    }

    public String getBoardRepresentativeName() {
        return boardRepresentativeName;
    }

    public void setBoardRepresentativeName(String boardRepresentativeName) {
        this.boardRepresentativeName = boardRepresentativeName;
    }

    public String getBoardRepresentativeDesignation() {
        return boardRepresentativeDesignation;
    }

    public void setBoardRepresentativeDesignation(String boardRepresentativeDesignation) {
        this.boardRepresentativeDesignation = boardRepresentativeDesignation;
    }

    public String getBoardRepresentativeAddress() {
        return boardRepresentativeAddress;
    }

    public void setBoardRepresentativeAddress(String boardRepresentativeAddress) {
        this.boardRepresentativeAddress = boardRepresentativeAddress;
    }

    public LocalDate getRecruitApproveGBResolutionDate() {
        return recruitApproveGBResolutionDate;
    }

    public void setRecruitApproveGBResolutionDate(LocalDate recruitApproveGBResolutionDate) {
        this.recruitApproveGBResolutionDate = recruitApproveGBResolutionDate;
    }

    public String getRecruitPermitAgendaNo() {
        return recruitPermitAgendaNo;
    }

    public void setRecruitPermitAgendaNo(String recruitPermitAgendaNo) {
        this.recruitPermitAgendaNo = recruitPermitAgendaNo;
    }

    public LocalDate getRecruitmentDate() {
        return recruitmentDate;
    }

    public void setRecruitmentDate(LocalDate recruitmentDate) {
        this.recruitmentDate = recruitmentDate;
    }

    public LocalDate getPresentInstituteJoinDate() {
        return presentInstituteJoinDate;
    }

    public void setPresentInstituteJoinDate(LocalDate presentInstituteJoinDate) {
        this.presentInstituteJoinDate = presentInstituteJoinDate;
    }

    public LocalDate getPresentPostJoinDate() {
        return presentPostJoinDate;
    }

    public void setPresentPostJoinDate(LocalDate presentPostJoinDate) {
        this.presentPostJoinDate = presentPostJoinDate;
    }

    public LocalDate getTimeScaleGBResolutionDate() {
        return timeScaleGBResolutionDate;
    }

    public void setTimeScaleGBResolutionDate(LocalDate timeScaleGBResolutionDate) {
        this.timeScaleGBResolutionDate = timeScaleGBResolutionDate;
    }

    public Integer getTotalJobDuration() {
        return totalJobDuration;
    }

    public void setTotalJobDuration(Integer totalJobDuration) {
        this.totalJobDuration = totalJobDuration;
    }

    public Integer getCurrentPositionJobDuration() {
        return currentPositionJobDuration;
    }

    public void setCurrentPositionJobDuration(Integer currentPositionJobDuration) {
        this.currentPositionJobDuration = currentPositionJobDuration;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User user) {
        this.manager = user;
    }

    public Institute getInstitute() {
        return institute;
    }

    public void setInstitute(Institute institute) {
        this.institute = institute;
    }

    public Set<PayScale> getPayScales() {
        return payScales;
    }

    public void setPayScales(Set<PayScale> payScales) {
        this.payScales = payScales;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<ApplicantEducation> getApplicantEducations() {
        return applicantEducations;
    }

    public void setApplicantEducations(Set<ApplicantEducation> applicantEducations) {
        this.applicantEducations = applicantEducations;
    }

    public Set<Training> getTrainings() {
        return trainings;
    }

    public void setTrainings(Set<Training> trainings) {
        this.trainings = trainings;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public Set<Reference> getReferences() {
        return references;
    }

    public void setReferences(Set<Reference> references) {
        this.references = references;
    }

    public Set<Lang> getLangs() {
        return langs;
    }

    public void setLangs(Set<Lang> langs) {
        this.langs = langs;
    }

    public EmployeeCategory getCategory() {
        return category;
    }

    public void setCategory(EmployeeCategory category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Employee employee = (Employee) o;

        if (!Objects.equals(id, employee.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Employee{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", nameEnglish='" + nameEnglish + "'" +
            ", father='" + father + "'" +
            ", mother='" + mother + "'" +
            ", presentAddress='" + presentAddress + "'" +
            ", permanentAddress='" + permanentAddress + "'" +
            ", gender='" + gender + "'" +
            ", dob='" + dob + "'" +
            ", zipCode='" + zipCode + "'" +
            ", registrationCertificateSubject='" + registrationCertificateSubject + "'" +
            ", registrationExamYear='" + registrationExamYear + "'" +
            ", registrationCetificateNo='" + registrationCetificateNo + "'" +
            ", indexNo='" + indexNo + "'" +
            ", bankName='" + bankName + "'" +
            ", bankBranch='" + bankBranch + "'" +
            ", bankAccountNo='" + bankAccountNo + "'" +
            ", designation='" + designation + "'" +
            ", subject='" + subject + "'" +
            ", salaryScale='" + salaryScale + "'" +
            ", salaryCode='" + salaryCode + "'" +
            ", monthlySalaryGovtProvided='" + monthlySalaryGovtProvided + "'" +
            ", monthlySalaryInstituteProvided='" + monthlySalaryInstituteProvided + "'" +
            ", gbResolutionReceiveDate='" + gbResolutionReceiveDate + "'" +
            ", gbResolutionAgendaNo='" + gbResolutionAgendaNo + "'" +
            ", circularPaperName='" + circularPaperName + "'" +
            ", circularPublishedDate='" + circularPublishedDate + "'" +
            ", recruitExamReceiveDate='" + recruitExamReceiveDate + "'" +
            ", dgRepresentativeName='" + dgRepresentativeName + "'" +
            ", dgRepresentativeDesignation='" + dgRepresentativeDesignation + "'" +
            ", dgRepresentativeAddress='" + dgRepresentativeAddress + "'" +
            ", boardRepresentativeName='" + boardRepresentativeName + "'" +
            ", boardRepresentativeDesignation='" + boardRepresentativeDesignation + "'" +
            ", boardRepresentativeAddress='" + boardRepresentativeAddress + "'" +
            ", recruitApproveGBResolutionDate='" + recruitApproveGBResolutionDate + "'" +
            ", recruitPermitAgendaNo='" + recruitPermitAgendaNo + "'" +
            ", recruitmentDate='" + recruitmentDate + "'" +
            ", presentInstituteJoinDate='" + presentInstituteJoinDate + "'" +
            ", presentPostJoinDate='" + presentPostJoinDate + "'" +
            ", timeScaleGBResolutionDate='" + timeScaleGBResolutionDate + "'" +
            ", totalJobDuration='" + totalJobDuration + "'" +
            ", currentPositionJobDuration='" + currentPositionJobDuration + "'" +
            ", cv='" + cv + "'" +
            ", cvContentType='" + cvContentType + "'" +
            ", category='" + category + "'" +
            '}';
    }
}
