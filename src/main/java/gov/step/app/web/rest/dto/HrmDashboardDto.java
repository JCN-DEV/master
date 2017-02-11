package gov.step.app.web.rest.dto;

import java.time.LocalDate;
import java.util.Date;

/**
 * Created by yzaman on 3/7/16.
 */
public class HrmDashboardDto
{
    private Long entityId;
    private Long employeeId;
    private String entityName;
    private String requestFrom;
    private String requestSummary;
    private Date requestDate;
    private Long logStatus;
    private String logComments;
	private Long logId;
	private Long isSelected;
	private String customId;

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getRequestFrom() {
        return requestFrom;
    }

    public void setRequestFrom(String requestFrom) {
        this.requestFrom = requestFrom;
    }

    public String getRequestSummary() {
        return requestSummary;
    }

    public void setRequestSummary(String requestSummary) {
        this.requestSummary = requestSummary;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public Long getLogStatus() {
        return logStatus;
    }

    public void setLogStatus(Long logStatus) {
        this.logStatus = logStatus;
    }

    public String getLogComments() {
        return logComments;
    }

    public void setLogComments(String logComments) {
        this.logComments = logComments;
    }

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Long getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(Long isSelected) {
        this.isSelected = isSelected;
    }

    public String getCustomId() {
        return customId;
    }

    public void setCustomId(String customId) {
        this.customId = customId;
    }
}
