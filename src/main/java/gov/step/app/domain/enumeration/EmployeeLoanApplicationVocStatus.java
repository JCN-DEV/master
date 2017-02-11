package gov.step.app.domain.enumeration;

/**
 * Created by bappimazumder on 10/4/16.
 */
public class EmployeeLoanApplicationVocStatus {

    private final Integer code;
    private final String remarks;
    private final String declineRemarks;
    private final String role;
    private final String viewMessage;

    public static final int TOTAL = 9;

    private EmployeeLoanApplicationVocStatus(Integer c, String n, String r, String declineRemarks, String viewMessage){
        code = c;
        remarks = n;
        role = r;
        this.declineRemarks = declineRemarks;
        this.viewMessage = viewMessage;
    }
    public static final EmployeeLoanApplicationVocStatus LOAN_INSTALLMENT_COMPLETED = new EmployeeLoanApplicationVocStatus(-2,"Installment Completed","","","");
    public static final EmployeeLoanApplicationVocStatus REJECTED = new EmployeeLoanApplicationVocStatus(-1,"Application Rejected","ROLE_DG","","");
    public static final EmployeeLoanApplicationVocStatus DECLINED = new EmployeeLoanApplicationVocStatus(0,"Application Declined","ROLE_BLANK","","");
    public static final EmployeeLoanApplicationVocStatus APPLICATION_COMPLETED = new EmployeeLoanApplicationVocStatus(1,"Application Completed","ROLE_INSTEMP","","Applicant");
    public static final EmployeeLoanApplicationVocStatus APPROVED_BY_INSTITUTE = new EmployeeLoanApplicationVocStatus(2,"Forwarded From Institute","ROLE_INSTITUTE","","Institute");
    public static final EmployeeLoanApplicationVocStatus APPROVED_BY_DG = new EmployeeLoanApplicationVocStatus(3,"Forwarded From DG","ROLE_DG","","DG");
    public static final EmployeeLoanApplicationVocStatus APPROVED_BY_DIRECTOR_VOC = new EmployeeLoanApplicationVocStatus(4,"Forwarded From Director(Vocational)","ROLE_DIRECTOR_VOC","","Director(Vocational)");
    public static final EmployeeLoanApplicationVocStatus APPROVED_BY_AD_VOC = new EmployeeLoanApplicationVocStatus(5,"Forwarded From AD(Vocational)","ROLE_ADVOC","","AD(Vocational)");
    public static final EmployeeLoanApplicationVocStatus APPROVED_BY_MINISTRY = new EmployeeLoanApplicationVocStatus(6,"Forwarded From Ministry","ROLE_MINISTRY","","Ministry");

    private static final EmployeeLoanApplicationVocStatus[] list = {

               //  REJECTED,
                DECLINED,
                APPLICATION_COMPLETED,
                APPROVED_BY_INSTITUTE,
                APPROVED_BY_DG,
                APPROVED_BY_DIRECTOR_VOC,
                APPROVED_BY_AD_VOC,
                APPROVED_BY_MINISTRY,
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

    public static EmployeeLoanApplicationVocStatus fromInt(int c) {
        if (c < -1 || c > TOTAL)
            throw new RuntimeException("Unknown code for fromInt in EmployeeLoanApplicationVocStatus");
        return list[c];
    }

    public static EmployeeLoanApplicationVocStatus nextLoanApplicationStatusType(int c) {
        if (c < 0 || c > TOTAL)
            throw new RuntimeException("Unknown code for nextLoanApplicationStatusType in EmployeeLoanApplicationVocStatus");
        return list[c+1];
    }
    public static EmployeeLoanApplicationVocStatus previousLoanApplicationStatusType(int c) {
        if (c < 0 || c > TOTAL)
            throw new RuntimeException("Unknown code for previousLoanApplicationStatusType in EmployeeLoanApplicationVocStatus");
        return list[c-1];
    }

    public static EmployeeLoanApplicationVocStatus currentLoanApplicationStatusType(int c) {
        if (c < 0 || c > TOTAL)
            throw new RuntimeException("Unknown code for currentLoanApplicationStatusType in EmployeeLoanApplicationVocStatus");
        return list[c];
    }

    public static EmployeeLoanApplicationVocStatus getLoanApplicationStatusByRole(String role){

        switch(role) {

            case "ROLE_INSTEMP":return APPLICATION_COMPLETED;

            case "ROLE_INSTITUTE": return APPROVED_BY_INSTITUTE;

            case "ROLE_DG": return APPROVED_BY_DG;

            case "ROLE_DIRECTOR_VOC": return APPROVED_BY_DIRECTOR_VOC;

            case "ROLE_ADVOC": return APPROVED_BY_AD_VOC;

            case "ROLE_MINISTRY":return APPROVED_BY_MINISTRY;

            default: return null;
        }
    }

    public static final EmployeeLoanApplicationVocStatus [] getVocStatusList = {
        APPROVED_BY_INSTITUTE,
        APPROVED_BY_DG,
        APPROVED_BY_DIRECTOR_VOC,
        APPROVED_BY_AD_VOC,
        APPROVED_BY_MINISTRY
    };

}
