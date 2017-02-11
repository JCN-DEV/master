package gov.step.app.web.rest.dto;

/**
 * Created by yzaman on 2/6/16.
 */
public class HrmApprovalDto
{
    private Long entityId;
    private Long logStatus;
    private Long logId;
    private String logComments;
    private String actionType;
    private String entityName;
    private Object entityObject;
    private Object entityLogObject;

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public Long getLogStatus() {
        return logStatus;
    }

    public void setLogStatus(Long logStatus) {
        this.logStatus = logStatus;
    }

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public String getLogComments() {
        return logComments;
    }

    public void setLogComments(String logComments) {
        this.logComments = logComments;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getEntityName() { return entityName;}

    public void setEntityName(String entityName) {this.entityName = entityName; }

    public Object getEntityObject() {return entityObject;}

    public void setEntityObject(Object entityObject) {this.entityObject = entityObject;}

    public Object getEntityLogObject() {return entityLogObject;}

    public void setEntityLogObject(Object entityLogObject) {this.entityLogObject = entityLogObject;}
}
