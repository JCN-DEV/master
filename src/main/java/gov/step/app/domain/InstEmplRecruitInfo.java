package gov.step.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A InstEmplRecruitInfo.
 */
@Entity
@Table(name = "inst_empl_recruit_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "instemplrecruitinfo")
public class InstEmplRecruitInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    @NotNull
    @Size(max = 30)
    @Column(name = "salary_scale", length = 30)
    private String salaryScale;

//    @NotNull
    @Size(max = 20)
    @Column(name = "salary_code", length = 20)
    private String salaryCode;

    @NotNull
    @Column(name = "month_salry_govt_provided", precision=10, scale=2, nullable = false)
    private BigDecimal monthlySalaryGovtProvided;

    @NotNull
    @Column(name = "month_salry_inst_provided", precision=10, scale=2, nullable = false)
    private BigDecimal monthlySalaryInstituteProvided;

    @NotNull
    @Column(name = "gb_resol_receive_date", nullable = false)
    private LocalDate gbResolutionReceiveDate;

    @NotNull
    @Size(max = 30)
    @Column(name = "gb_resol_agenda_no", length = 30, nullable = false)
    private String gbResolutionAgendaNo;

    @NotNull
    @Size(max = 50)
    @Column(name = "circular_paper_name", length = 50, nullable = false)
    private String circularPaperName;

    @NotNull
    @Size(max = 50)
    @Column(name = "circular_national_paper_name", length = 50, nullable = false)
    private String circularNationalPaperName;

    @NotNull
    @Column(name = "circular_published_date", nullable = false)
    private LocalDate circularPublishedDate;

    @NotNull
    @Column(name = "circular_National_Pub_Date", nullable = false)
    private LocalDate circularNationalPubDt;

    @NotNull
    @Column(name = "exam_receive_date", nullable = false)
    private LocalDate recruitExamReceiveDate;

    @NotNull
    @Size(max = 50)
    @Column(name = "dg_represent_name", length = 50, nullable = false)
    private String dgRepresentativeName;

    @NotNull
    @Size(max = 50)
    @Column(name = "dg_represent_designa", length = 50, nullable = false)
    private String dgRepresentativeDesignation;

    @NotNull
    @Size(max = 60)
    @Column(name = "dg_represent_addr", length = 60, nullable = false)
    private String dgRepresentativeAddress;

    @NotNull
    @Size(max = 50)
    @Column(name = "board_represent_name", length = 50, nullable = false)
    private String boardRepresentativeName;

    @NotNull
    @Size(max = 50)
    @Column(name = "board_represent_designa", length = 50, nullable = false)
    private String boardRepresentativeDesignation;

    @NotNull
    @Size(max = 127)
    @Column(name = "board_represent_addr", length = 127, nullable = false)
    private String boardRepresentativeAddress;

    @NotNull
    @Column(name = "approve_gbresol_date", nullable = false)
    private LocalDate recruitApproveGBResolutionDate;

    @Size(max = 50)
    @Column(name = "permit_agenda_no", length = 50)
    private String recruitPermitAgendaNo;

    @NotNull
    @Column(name = "recruitment_date", nullable = false)
    private LocalDate recruitmentDate;

    @NotNull
    @Column(name = "present_inst_join_date", nullable = false)
    private LocalDate presentInstituteJoinDate;

    @NotNull
    @Column(name = "present_post_join_date", nullable = false)
    private LocalDate presentPostJoinDate;

    //@NotNull
    @Column(name = "dg_represent_recrd_no")
    private String dgRepresentativeRecordNo;

    //@NotNull
    @Column(name = "board_represent_recrd_no")
    private String boardRepresentativeRecordNo;

//    @NotNull
    @Column(name = "dg_represent_recrd_Date")
    private LocalDate dgRepresentativeRecordDate;

    //@NotNull
    @Column(name = "board_represent_recrd_Date")
    private LocalDate boardRepresentativeRecordDate;

    @Lob
    @Column(name = "appoinment_ltr")
    private byte[] appoinmentLtr;


    @Column(name = "appoinment_ltr_cnt_type")
    private String appoinmentLtrContentType;

    @Column(name = "appoinment_ltr_name")
    private String appoinmentLtrName;

    @Lob
    @Column(name = "joining_letter")
    private byte[] joiningLetter;


    @Column(name = "joining_letter_cnt_type")
    private String joiningLetterContentType;

    @Column(name = "joining_letter_name")
    private String joiningLetterName;

    @Lob
    @Column(name = "recruit_result")
    private byte[] recruitResult;



    @Column(name = "recruit_result_cnt_type")
    private String recruitResultContentType;

    @Column(name = "recruit_result_name")
    private String recruitResultName;

    @Lob
    @Column(name = "recruit_news_local")
    private byte[] recruitNewsLocal;


    @Column(name = "recruit_news_local_cnt_type")
    private String recruitNewsLocalContentType;

    @Column(name = "recruit_news_local_name")
    private String recruitNewsLocalName;

    @Lob
    @Column(name = "recruit_news_daily")
    private byte[] recruitNewsDaily;


    @Column(name = "recruit_news_daily_cnt_type")
    private String recruitNewsDailyContentType;

    @Column(name = "recruit_news_daily_name")
    private String recruitNewsDailyName;

    @Lob
    @Column(name = "attachment")
    private byte[] attachment;


    @Column(name = "attachment_cnt_type")
    private String attachmentContentType;

    @Column(name = "attachment_name")
    private String attachmentName;

    @Lob
    @Column(name = "go_resulaton")
    private byte[] goResulaton;


    @Column(name = "go_resulaton_cnt_type")
    private String goResulatonContentType;

    @Column(name = "go_resulaton_name")
    private String goResulatonName;

    @Lob
    @Column(name = "committee")
    private byte[] committee;


    @Column(name = "committee_cnt_type")
    private String committeeContentType;

    @Column(name = "committee_name")
    private String committeeName;

    @Lob
    @Column(name = "recommendation")
    private byte[] recommendation;


    @Column(name = "recommendation_cnt_type")
    private String recommendationContentType;

    @Column(name = "recommendation_name")
    private String recommendationName;

    @Lob
    @Column(name = "sanction")
    private byte[] sanction;


    @Column(name = "sanction_cnt_type")
    private String sanctionContentType;

    @Column(name = "sanction_name")
    private String sanctionName;

    @Column(name = "department")
    private String department;

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
    @JoinColumn(name = "inst_employee_id")
    private InstEmployee instEmployee;

    @ManyToOne
    @JoinColumn(name = "pay_scale_id")
    private PayScale payScale;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCircularNationalPaperName() {
        return circularNationalPaperName;
    }

    public void setCircularNationalPaperName(String circularNationalPaperName) {
        this.circularNationalPaperName = circularNationalPaperName;
    }

    public LocalDate getCircularPublishedDate() {
        return circularPublishedDate;
    }

    public void setCircularPublishedDate(LocalDate circularPublishedDate) {
        this.circularPublishedDate = circularPublishedDate;
    }

    public LocalDate getCircularNationalPubDt() {
        return circularNationalPubDt;
    }

    public void setCircularNationalPubDt(LocalDate circularNationalPubDt) {
        this.circularNationalPubDt = circularNationalPubDt;
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

    public String getDgRepresentativeRecordNo() {
        return dgRepresentativeRecordNo;
    }

    public void setDgRepresentativeRecordNo(String dgRepresentativeRecordNo) {
        this.dgRepresentativeRecordNo = dgRepresentativeRecordNo;
    }

    public String getBoardRepresentativeRecordNo() {
        return boardRepresentativeRecordNo;
    }

    public void setBoardRepresentativeRecordNo(String boardRepresentativeRecordNo) {
        this.boardRepresentativeRecordNo = boardRepresentativeRecordNo;
    }

    public LocalDate getDgRepresentativeRecordDate() {
        return dgRepresentativeRecordDate;
    }

    public void setDgRepresentativeRecordDate(LocalDate dgRepresentativeRecordDate) {
        this.dgRepresentativeRecordDate = dgRepresentativeRecordDate;
    }

    public LocalDate getBoardRepresentativeRecordDate() {
        return boardRepresentativeRecordDate;
    }

    public void setBoardRepresentativeRecordDate(LocalDate boardRepresentativeRecordDate) {
        this.boardRepresentativeRecordDate = boardRepresentativeRecordDate;
    }

    public byte[] getAppoinmentLtr() {
        return appoinmentLtr;
    }

    public void setAppoinmentLtr(byte[] appoinmentLtr) {
        this.appoinmentLtr = appoinmentLtr;
    }

    public String getAppoinmentLtrContentType() {
        return appoinmentLtrContentType;
    }

    public void setAppoinmentLtrContentType(String appoinmentLtrContentType) {
        this.appoinmentLtrContentType = appoinmentLtrContentType;
    }

    public String getAppoinmentLtrName() {
        return appoinmentLtrName;
    }

    public void setAppoinmentLtrName(String appoinmentLtrName) {
        this.appoinmentLtrName = appoinmentLtrName;
    }

    public byte[] getJoiningLetter() {
        return joiningLetter;
    }

    public void setJoiningLetter(byte[] joiningLetter) {
        this.joiningLetter = joiningLetter;
    }

    public String getJoiningLetterContentType() {
        return joiningLetterContentType;
    }

    public void setJoiningLetterContentType(String joiningLetterContentType) {
        this.joiningLetterContentType = joiningLetterContentType;
    }

    public String getJoiningLetterName() {
        return joiningLetterName;
    }

    public void setJoiningLetterName(String joiningLetterName) {
        this.joiningLetterName = joiningLetterName;
    }

    public byte[] getRecruitResult() {
        return recruitResult;
    }

    public void setRecruitResult(byte[] recruitResult) {
        this.recruitResult = recruitResult;
    }

    public String getRecruitResultContentType() {
        return recruitResultContentType;
    }

    public void setRecruitResultContentType(String recruitResultContentType) {
        this.recruitResultContentType = recruitResultContentType;
    }

    public String getRecruitResultName() {
        return recruitResultName;
    }

    public void setRecruitResultName(String recruitResultName) {
        this.recruitResultName = recruitResultName;
    }

    public byte[] getRecruitNewsLocal() {
        return recruitNewsLocal;
    }

    public void setRecruitNewsLocal(byte[] recruitNewsLocal) {
        this.recruitNewsLocal = recruitNewsLocal;
    }

    public String getRecruitNewsLocalContentType() {
        return recruitNewsLocalContentType;
    }

    public void setRecruitNewsLocalContentType(String recruitNewsLocalContentType) {
        this.recruitNewsLocalContentType = recruitNewsLocalContentType;
    }

    public String getRecruitNewsLocalName() {
        return recruitNewsLocalName;
    }

    public void setRecruitNewsLocalName(String recruitNewsLocalName) {
        this.recruitNewsLocalName = recruitNewsLocalName;
    }

    public byte[] getRecruitNewsDaily() {
        return recruitNewsDaily;
    }

    public void setRecruitNewsDaily(byte[] recruitNewsDaily) {
        this.recruitNewsDaily = recruitNewsDaily;
    }

    public String getRecruitNewsDailyContentType() {
        return recruitNewsDailyContentType;
    }

    public void setRecruitNewsDailyContentType(String recruitNewsDailyContentType) {
        this.recruitNewsDailyContentType = recruitNewsDailyContentType;
    }

    public String getRecruitNewsDailyName() {
        return recruitNewsDailyName;
    }

    public void setRecruitNewsDailyName(String recruitNewsDailyName) {
        this.recruitNewsDailyName = recruitNewsDailyName;
    }

    public byte[] getAttachment() {
        return attachment;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }

    public String getAttachmentContentType() {
        return attachmentContentType;
    }

    public void setAttachmentContentType(String attachmentContentType) {
        this.attachmentContentType = attachmentContentType;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public byte[] getGoResulaton() {
        return goResulaton;
    }

    public void setGoResulaton(byte[] goResulaton) {
        this.goResulaton = goResulaton;
    }

    public String getGoResulatonContentType() {
        return goResulatonContentType;
    }

    public void setGoResulatonContentType(String goResulatonContentType) {
        this.goResulatonContentType = goResulatonContentType;
    }

    public String getGoResulatonName() {
        return goResulatonName;
    }

    public void setGoResulatonName(String goResulatonName) {
        this.goResulatonName = goResulatonName;
    }

    public byte[] getCommittee() {
        return committee;
    }

    public void setCommittee(byte[] committee) {
        this.committee = committee;
    }

    public String getCommitteeContentType() {
        return committeeContentType;
    }

    public void setCommitteeContentType(String committeeContentType) {
        this.committeeContentType = committeeContentType;
    }

    public String getCommitteeName() {
        return committeeName;
    }

    public void setCommitteeName(String committeeName) {
        this.committeeName = committeeName;
    }

    public byte[] getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(byte[] recommendation) {
        this.recommendation = recommendation;
    }

    public String getRecommendationContentType() {
        return recommendationContentType;
    }

    public void setRecommendationContentType(String recommendationContentType) {
        this.recommendationContentType = recommendationContentType;
    }

    public String getRecommendationName() {
        return recommendationName;
    }

    public void setRecommendationName(String recommendationName) {
        this.recommendationName = recommendationName;
    }

    public byte[] getSanction() {
        return sanction;
    }

    public void setSanction(byte[] sanction) {
        this.sanction = sanction;
    }

    public String getSanctionContentType() {
        return sanctionContentType;
    }

    public void setSanctionContentType(String sanctionContentType) {
        this.sanctionContentType = sanctionContentType;
    }

    public String getSanctionName() {
        return sanctionName;
    }

    public void setSanctionName(String sanctionName) {
        this.sanctionName = sanctionName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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

    public InstEmployee getInstEmployee() {
        return instEmployee;
    }

    public void setInstEmployee(InstEmployee instEmployee) {
        this.instEmployee = instEmployee;
    }

    public PayScale getPayScale() {
        return payScale;
    }

    public void setPayScale(PayScale payScale) {
        this.payScale = payScale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InstEmplRecruitInfo instEmplRecruitInfo = (InstEmplRecruitInfo) o;

        if ( ! Objects.equals(id, instEmplRecruitInfo.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InstEmplRecruitInfo{" +
            "id=" + id +
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
            ", dgRepresentativeRecordNo='" + dgRepresentativeRecordNo + "'" +
            ", boardRepresentativeRecordNo='" + boardRepresentativeRecordNo + "'" +
            ", department='" + department + "'" +
            '}';
    }
}
