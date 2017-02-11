package gov.step.app.domain.enumeration;

/**
 * Created by bappimazumder on 11/20/16.
 */
public class EmployeeLoanApplicationOthersStatus {


    private final Integer code;
    private final String remarks;
    private final String declineRemarks;
    private final String role;
    private final String viewMessage;

    public static final int TOTAL = 10;

    private EmployeeLoanApplicationOthersStatus(Integer c, String n, String r, String declineRemarks, String viewMessage){
        code = c;
        remarks = n;
        role = r;
        this.declineRemarks = declineRemarks;
        this.viewMessage = viewMessage;
    }
    public static final EmployeeLoanApplicationOthersStatus LOAN_INSTALLMENT_COMPLETED = new EmployeeLoanApplicationOthersStatus(-2,"Installment Completed","","","");
    public static final EmployeeLoanApplicationOthersStatus REJECTED = new EmployeeLoanApplicationOthersStatus(-1,"Application Rejected","ROLE_DG","","");
    public static final EmployeeLoanApplicationOthersStatus DECLINED = new EmployeeLoanApplicationOthersStatus(0,"Application Declined","ROLE_BLANK","","");
    public static final EmployeeLoanApplicationOthersStatus APPLICATION_COMPLETED = new EmployeeLoanApplicationOthersStatus(1,"Application Completed","ROLE_INSTEMP","","Applicant");
    public static final EmployeeLoanApplicationOthersStatus APPROVED_BY_INSTITUTE = new EmployeeLoanApplicationOthersStatus(2,"Forwarded From Institute","ROLE_INSTITUTE","","Institute");
    public static final EmployeeLoanApplicationOthersStatus APPROVED_BY_DG = new EmployeeLoanApplicationOthersStatus(3,"Forwarded From DG","ROLE_DG","","DG");
    public static final EmployeeLoanApplicationOthersStatus APPROVED_BY_DIRECTOR_ADMIN = new EmployeeLoanApplicationOthersStatus(4,"Forwarded From Director Admin","ROLE_DIRECTOR_ADMIN","","Director(Admin)");
    public static final EmployeeLoanApplicationOthersStatus APPROVED_BY_AD3 = new EmployeeLoanApplicationOthersStatus(5,"Forwarded From AD3","ROLE_AD3","","AD3");
    public static final EmployeeLoanApplicationOthersStatus APPROVED_BY_AO = new EmployeeLoanApplicationOthersStatus(6,"Forwarded From AO","ROLE_AO","","AO");
    public static final EmployeeLoanApplicationOthersStatus APPROVED_BY_MINISTRY = new EmployeeLoanApplicationOthersStatus(7,"Forwarded From Ministry","ROLE_MINISTRY","","Ministry");

    private static final EmployeeLoanApplicationOthersStatus[] list = {

        //  REJECTED,
        DECLINED,
        APPLICATION_COMPLETED,
        APPROVED_BY_INSTITUTE,
        APPROVED_BY_DG,
        APPROVED_BY_DIRECTOR_ADMIN,
        APPROVED_BY_AD3,
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

    public static EmployeeLoanApplicationOthersStatus fromInt(int c) {
        if (c < -1 || c > TOTAL)
            throw new RuntimeException("Unknown code for fromInt in EmployeeLoanApplicationVocStatus");
        return list[c];
    }

    public static EmployeeLoanApplicationOthersStatus nextLoanApplicationStatusType(int c) {
        if (c < 0 || c > TOTAL)
            throw new RuntimeException("Unknown code for nextLoanApplicationStatusType in EmployeeLoanApplicationVocStatus");
        return list[c+1];
    }
    public static EmployeeLoanApplicationOthersStatus previousLoanApplicationStatusType(int c) {
        if (c < 0 || c > TOTAL)
            throw new RuntimeException("Unknown code for previousLoanApplicationStatusType in EmployeeLoanApplicationVocStatus");
        return list[c-1];
    }
    public static EmployeeLoanApplicationOthersStatus currentLoanApplicationStatusType(int c) {
        if (c < 0 || c > TOTAL)
            throw new RuntimeException("Unknown code for currentLoanApplicationStatusType in EmployeeLoanApplicationVocStatus");
        return list[c];
    }

    public static EmployeeLoanApplicationOthersStatus getLoanApplicationStatusByRole(String role){

        switch(role) {
            case "ROLE_INSTEMP":return APPLICATION_COMPLETED;

            case "ROLE_INSTITUTE": return APPROVED_BY_INSTITUTE;

            case "ROLE_DG": return APPROVED_BY_DG;

            case "ROLE_DIRECTOR_ADMIN": return APPROVED_BY_DIRECTOR_ADMIN;

            case "ROLE_AD3":return APPROVED_BY_AD3;

            case "ROLE_AO": return APPROVED_BY_AO;

            case "ROLE_MINISTRY" : return  APPROVED_BY_MINISTRY;

            default: return null;
            // etc...
        }

    }

    public static final EmployeeLoanApplicationOthersStatus [] getOthersStatusList = {
        APPROVED_BY_INSTITUTE,
        APPROVED_BY_DG,
        APPROVED_BY_DIRECTOR_ADMIN,
        APPROVED_BY_AD3,
        APPROVED_BY_AO,
        APPROVED_BY_MINISTRY
    };

}
