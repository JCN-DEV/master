package gov.step.app.web.rest.dto;

/**
 * Created by ayon on 7/19/16.
 */
public class RisJobPostingDTO {

    private String circularNo;
    private String educationalQualification;
    private String otherQualification;
    private String remarks;
    private String publishDate;
    private String applicationDate;
    private String attachmentContentType;
    private String createdDate;
    private String updatedDate;
    private String status;
    private String attachmentImageName;
    private String positionName;
    private String vacantPosition;
    private String department;
    private byte[] attachment;

    public byte[] getAttachment() {
        return attachment;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }

    public String getCircularNo() {
        return circularNo;
    }

    public void setCircularNo(String circularNo) {
        this.circularNo = circularNo;
    }

    public String getEducationalQualification() {
        return educationalQualification;
    }

    public void setEducationalQualification(String educationalQualification) {
        this.educationalQualification = educationalQualification;
    }

    public String getOtherQualification() {
        return otherQualification;
    }

    public void setOtherQualification(String otherQualification) {
        this.otherQualification = otherQualification;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getAttachmentContentType() {
        return attachmentContentType;
    }

    public void setAttachmentContentType(String attachmentContentType) {
        this.attachmentContentType = attachmentContentType;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAttachmentImageName() {
        return attachmentImageName;
    }

    public void setAttachmentImageName(String attachmentImageName) {
        this.attachmentImageName = attachmentImageName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getVacantPosition() {
        return vacantPosition;
    }

    public void setVacantPosition(String vacantPosition) {
        this.vacantPosition = vacantPosition;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }


    @Override
    public String toString() {
        return "RisJobPostingDTO{" +
            "circularNo='" + circularNo + '\'' +
            ", educationalQualification='" + educationalQualification + '\'' +
            ", otherQualification='" + otherQualification + '\'' +
            ", remarks='" + remarks + '\'' +
            ", publishDate='" + publishDate + '\'' +
            ", applicationDate='" + applicationDate + '\'' +
            ", attachmentContentType='" + attachmentContentType + '\'' +
            ", createdDate='" + createdDate + '\'' +
            ", updatedDate='" + updatedDate + '\'' +
            ", status='" + status + '\'' +
            ", attachmentImageName='" + attachmentImageName + '\'' +
            ", positionName='" + positionName + '\'' +
            ", vacantPosition='" + vacantPosition + '\'' +
            ", department='" + department + '\'' +
            '}';
    }
}
