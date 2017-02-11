package gov.step.app.service.constnt;

/**
 * Created by yzaman on 1/16/16.
 */
public interface HRMManagementConstant
{
    public final String ROLE_DTE_EMPLOYEE_NAME  = "ROLE_DTE_EMPLOYEE";
    public final String ROLE_HRM_USER_NAME      = "ROLE_HRM_USER";

    public final String APPROVAL_LOG_STATUS_ACCEPT   = "accept";
    public final String APPROVAL_LOG_STATUS_REJECT   = "reject";
    public final String EMPLOYEE_DEACTIVATE          = "deactive";
    public final String EMPLOYEE_ACTIVATE            = "activate";

    public final long HR_EMPLOYEE_DEACTIVATED        = 5;
    public final long HR_EMPLOYEE_ACTIVATED          = 4;

    public final long APPROVAL_STATUS_LOCKED = 0;
    public final long APPROVAL_STATUS_FREE   = 1;
    public final long APPROVAL_STATUS_APPROVED = 2;
    public final long APPROVAL_STATUS_REJECTED = 3;
    public final long APPROVAL_STATUS_EMPINIT = 5;
    public final long APPROVAL_STATUS_ADMIN_ENTRY = 6;

    public final long APPROVAL_LOG_STATUS_ACTIVE = 1;
    public final long APPROVAL_LOG_STATUS_APPROVED   = 2;
    public final long APPROVAL_LOG_STATUS_ROLLBACKED = 3;
    public final long APPROVAL_LOG_STATUS_REJECTED   = 3;
    public final long DTE_TRANSFER_IN_LOG_STATUS = 8;

    public final int MINIMUM_IMG_NAME_LENGTH        = 4;
    public final String EMPLOYEE_PHOTO_FILE_DIR     = "data/hrm/employee/docs/";
    public final String EDUCATION_CERT_FILE_DIR     = "data/hrm/education/cert/";
    public final String EDUCATION_TRANS_FILE_DIR    = "data/hrm/education/trans/";

    public final String TRAINING_CERT_FILE_DIR      = "data/hrm/training/cert/";
    public final String TRAINING_GOORDER_FILE_DIR   = "data/hrm/training/goorder/";

    public final String AWARD_CERT_FILE_DIR         = "data/hrm/award/cert/";
    public final String AWARD_GOORDER_FILE_DIR      = "data/hrm/award/goorder/";

    public final String PUBLICATION_FILE_DIR        = "data/hrm/publication/";
    public final String TRANSFER_FILE_DIR           = "data/hrm/transfer/";
    public final String PROMOTION_FILE_DIR          = "data/hrm/promotion/";
    public final String ADV_INCREMENT_FILE_DIR      = "data/hrm/increment/";
    public final String ACTING_DUTY_FILE_DIR        = "data/hrm/actingduty/";
    public final String FOREIGN_TOUR_FILE_DIR       = "data/hrm/foreigntour/";
    public final String SERV_HISTORY_TOUR_FILE_DIR  = "data/hrm/servhistory/";
}
