package gov.step.app.domain.enumeration;

/**
 * Created by rana on 1/11/16.
 */
public class InstEmployeeDocumentStatus {

    private final int code;
    private final String remarks;
    private final String declineRemarks;
    private final String role;

    public static final int TOTAL = 5;

    private InstEmployeeDocumentStatus(int c, String n, String r, String declineRemarks) {
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

    public static final InstEmployeeDocumentStatus NEWENTRY
        = new InstEmployeeDocumentStatus(0, "New Application ","New Application","");

    public static final InstEmployeeDocumentStatus DECLINED
        = new InstEmployeeDocumentStatus(1, "Application Declined","DECLINED","");

    public static final InstEmployeeDocumentStatus APPROVED
        = new InstEmployeeDocumentStatus(2, "APPROVED","APPROVED","");


    private static final InstEmployeeDocumentStatus[] list = {
        NEWENTRY,
        DECLINED,
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

    public static InstEmployeeDocumentStatus fromInt(int c) {
        if (c < 1 || c > TOTAL)
            throw new RuntimeException("Unknown code for fromInt in MpoApplicationStatusType");
        return list[c];
    }
    public static InstEmployeeDocumentStatus nextMpoApplicationStatusType(int c) {
        if (c < 0 || c > TOTAL)
            throw new RuntimeException("Unknown code for fromInt in MpoApplicationStatusType");
        return list[c+1];
    }
    public static InstEmployeeDocumentStatus prevMpoApplicationStatusType(int c) {
        if (c < 0 || c > TOTAL)
            throw new RuntimeException("Unknown code for fromInt in MpoApplicationStatusType");
        return list[c-1];
    }

    /*public static List getMpoApplicationStatusList() {
        return Arrays.asList(list);
    }*/
}
