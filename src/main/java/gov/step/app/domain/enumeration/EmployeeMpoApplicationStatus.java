package gov.step.app.domain.enumeration;

/**
 * Created by leads on 12/10/15.
 */
public class EmployeeMpoApplicationStatus {

    private final int code;
    private final String remarks;
    private final String declineRemarks;
    private final String role;

    public static final int TOTAL = 5;

    private EmployeeMpoApplicationStatus(int c, String n, String r, String declineRemarks) {
        code = c;
        remarks = n;
        role = r;
        this.declineRemarks = declineRemarks;
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("code = [");
        buf.append(this.code);
        buf.append("]");
        buf.append(", name = [");
        buf.append(this.remarks);
        buf.append("]");
        return buf.toString();
    }

    public static final EmployeeMpoApplicationStatus NEWENTRY
        = new EmployeeMpoApplicationStatus(0, "New Application ","New Application","");

    public static final EmployeeMpoApplicationStatus DECLINED
        = new EmployeeMpoApplicationStatus(1, "Application Declined","DECLINED","");
    public static final EmployeeMpoApplicationStatus ELIGIBLE
        = new EmployeeMpoApplicationStatus(2, "ELIGIBLE for Application","ELIGIBLE","");

    public static final EmployeeMpoApplicationStatus APPLIED
        = new EmployeeMpoApplicationStatus(3, "APPLIED","ROLE_INSTITUTE","");

    public static final EmployeeMpoApplicationStatus APPLIED_AFTER_DECLINED
        = new EmployeeMpoApplicationStatus(4, "APPLIED AFTER DECLINED","APPLIED_AFTER_DECLINED","");

    public static final EmployeeMpoApplicationStatus APPROVED
        = new EmployeeMpoApplicationStatus(5, "APPROVED","APPROVED","");


    private static final EmployeeMpoApplicationStatus[] list = {
        NEWENTRY,
        DECLINED,
        ELIGIBLE,
        APPLIED,
        APPLIED_AFTER_DECLINED,
        APPROVED
    };

    public int toInt() {
        return code;
    }

    public int getCode() {
        return code;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getDeclineRemarks() {
        return declineRemarks;
    }

    public static EmployeeMpoApplicationStatus fromInt(int c) {
        if (c < 1 || c > TOTAL)
            throw new RuntimeException("Unknown code for fromInt in MpoApplicationStatusType");
        return list[c];
    }
    public static EmployeeMpoApplicationStatus nextMpoApplicationStatusType(int c) {
        if (c < 0 || c > TOTAL)
            throw new RuntimeException("Unknown code for fromInt in MpoApplicationStatusType");
        return list[c+1];
    }
    public static EmployeeMpoApplicationStatus prevMpoApplicationStatusType(int c) {
        if (c < 0 || c > TOTAL)
            throw new RuntimeException("Unknown code for fromInt in MpoApplicationStatusType");
        return list[c-1];
    }

    /*public static List getMpoApplicationStatusList() {
        return Arrays.asList(list);
    }*/
}
