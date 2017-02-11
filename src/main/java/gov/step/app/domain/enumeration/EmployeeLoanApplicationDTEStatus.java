package gov.step.app.domain.enumeration;

/**
 * Created by bappimazumder on 11/20/16.
 */
public class EmployeeLoanApplicationDTEStatus {


    private final Integer code;
    private final String remarks;
    private final String declineRemarks;
    private final String role;
    private final String viewMessage;

    public static final int TOTAL = 9;

    private EmployeeLoanApplicationDTEStatus(Integer c, String n, String r, String declineRemarks, String viewMessage){
        code = c;
        remarks = n;
        role = r;
        this.declineRemarks = declineRemarks;
        this.viewMessage = viewMessage;
    }
    public static final EmployeeLoanApplicationDTEStatus LOAN_INSTALLMENT_COMPLETED = new EmployeeLoanApplicationDTEStatus(-2,"Installment Completed","","","");
    public static final EmployeeLoanApplicationDTEStatus REJECTED = new EmployeeLoanApplicationDTEStatus(-1,"Application Rejected","ROLE_DG","","");
    public static final EmployeeLoanApplicationDTEStatus DECLINED = new EmployeeLoanApplicationDTEStatus(0,"Application Declined","ROLE_BLANK","","");
    public static final EmployeeLoanApplicationDTEStatus APPLICATION_COMPLETED = new EmployeeLoanApplicationDTEStatus(1,"Application Completed","ROLE_USER","","Applicant");
    public static final EmployeeLoanApplicationDTEStatus APPROVED_BY_DG = new EmployeeLoanApplicationDTEStatus(2,"Forwarded From DG","ROLE_DG","","DG");
    public static final EmployeeLoanApplicationDTEStatus APPROVED_BY_DTE_ADMIN = new EmployeeLoanApplicationDTEStatus(3,"Forwarded From DTE Admin","ROLE_DTEADMIN","","ADMIN(DTE)");
    public static final EmployeeLoanApplicationDTEStatus APPROVED_BY_AO = new EmployeeLoanApplicationDTEStatus(4,"Forwarded From AO","ROLE_AO","","AO");
    public static final EmployeeLoanApplicationDTEStatus APPROVED_BY_MINISTRY = new EmployeeLoanApplicationDTEStatus(7,"Forwarded From Ministry","ROLE_MINISTRY","","Ministry");

    private static final EmployeeLoanApplicationDTEStatus[] list = {

        //  REJECTED,
        DECLINED,
        APPLICATION_COMPLETED,
        APPROVED_BY_DG,
        APPROVED_BY_DTE_ADMIN,
        APPROVED_BY_AO,
        APPROVED_BY_MINISTRY
    };

    public int getCode() {
        return code;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getDeclineRemarks() {
        return declineRemarks;
    }

    public String getRole() {
        return role;
    }

    public String getViewMessage() {
        return viewMessage;
    }

    public static EmployeeLoanApplicationDTEStatus fromInt(int c) {
        if (c < -1 || c > TOTAL)
            throw new RuntimeException("Unknown code for fromInt in EmployeeLoanApplicationDTEStatus");
        return list[c];
    }

    public static EmployeeLoanApplicationDTEStatus nextLoanApplicationStatusType(int c) {
        if (c < 0 || c > TOTAL)
            throw new RuntimeException("Unknown code for nextLoanApplicationStatusType in EmployeeLoanApplicationDTEStatus");
        return list[c+1];
    }
    public static EmployeeLoanApplicationDTEStatus previousLoanApplicationStatusType(int c) {
        if (c < 0 || c > TOTAL)
            throw new RuntimeException("Unknown code for previousLoanApplicationStatusType in EmployeeLoanApplicationDTEStatus");
        return list[c-1];
    }
    public static EmployeeLoanApplicationDTEStatus currentLoanApplicationStatusType(int c) {
        if (c < 0 || c > TOTAL)
            throw new RuntimeException("Unknown code for currentLoanApplicationStatusType in EmployeeLoanApplicationVocStatus");
        return list[c];
    }

    public static EmployeeLoanApplicationDTEStatus getLoanApplicationStatusByRole(String role){

        switch(role) {
            case "ROLE_USER":return APPLICATION_COMPLETED;

            case "ROLE_DG": return APPROVED_BY_DG;

            case "ROLE_DTEADMIN": return APPROVED_BY_DTE_ADMIN;

            case "ROLE_AO": return APPROVED_BY_AO;

            case "ROLE_MINISTRY" : return APPROVED_BY_MINISTRY;

            default: return null;
            // etc...
        }

    }

    public static final EmployeeLoanApplicationDTEStatus [] getDTEStatusList = {
        APPROVED_BY_DG,
        APPROVED_BY_DTE_ADMIN,
        APPROVED_BY_AO,
        APPROVED_BY_MINISTRY
    };

}
