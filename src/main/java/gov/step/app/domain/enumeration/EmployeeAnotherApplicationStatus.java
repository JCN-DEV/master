package gov.step.app.domain.enumeration;

/**
 * Created by leads on 12/10/15.
 */
public class EmployeeAnotherApplicationStatus {

    private final int code;
    private final String remarks;
    private final String declineRemarks;
    private final String role;

    public static final int TOTAL = 5;

    private EmployeeAnotherApplicationStatus(int c, String n, String r, String declineRemarks) {
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

    public static final EmployeeAnotherApplicationStatus NEWENTRY
        = new EmployeeAnotherApplicationStatus(0, "New Application ","Application Not Completed ","");

    public static final EmployeeAnotherApplicationStatus REJECT
        = new EmployeeAnotherApplicationStatus(1, "Application Reject","REJECTED","");
    public static final EmployeeAnotherApplicationStatus PENDING
        = new EmployeeAnotherApplicationStatus(2, "Application pending","PENDING","");

   public static final EmployeeAnotherApplicationStatus APPROVED
        = new EmployeeAnotherApplicationStatus(5, "APPROVED","APPROVED","");

   public static final EmployeeAnotherApplicationStatus PAYSCALEASSIGNED
        = new EmployeeAnotherApplicationStatus(6, "PAY SCALE ASSIGNED","PAY SCALE ASSIGNED","");


    private static final EmployeeAnotherApplicationStatus[] list = {
        NEWENTRY,
        REJECT,
        PENDING,
        APPROVED,
        PAYSCALEASSIGNED
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

    public static EmployeeAnotherApplicationStatus fromInt(int c) {
        if (c < 1 || c > TOTAL)
            throw new RuntimeException("Unknown code for fromInt in MpoApplicationStatusType");
        return list[c];
    }
    public static EmployeeAnotherApplicationStatus nextMpoApplicationStatusType(int c) {
        if (c < 0 || c > TOTAL)
            throw new RuntimeException("Unknown code for fromInt in MpoApplicationStatusType");
        return list[c+1];
    }
    public static EmployeeAnotherApplicationStatus prevMpoApplicationStatusType(int c) {
        if (c < 0 || c > TOTAL)
            throw new RuntimeException("Unknown code for fromInt in MpoApplicationStatusType");
        return list[c-1];
    }

    /*public static List getMpoApplicationStatusList() {
        return Arrays.asList(list);
    }*/
}
