package gov.step.app.domain.enumeration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by leads on 12/10/15.
 */
public class MpoApplicationStatusType {

    private final int code;
    private final String remarks;
    private final String declineRemarks;
    private final String role;
    private final String viewMessage;

    public static final int TOTAL = 12;

    private MpoApplicationStatusType(int c, String n, String r,String declineRemarks,String viewMessage) {
        code = c;
        remarks = n;
        role = r;
        this.declineRemarks = declineRemarks;
        this.viewMessage = viewMessage;
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

    public static final MpoApplicationStatusType REJECTEED
        = new MpoApplicationStatusType(0, "Rejected ","ROLE_ALL","","");

    public static final MpoApplicationStatusType DECLINED
        = new MpoApplicationStatusType(1, "Application Declined","ROLE_BLANK","","");

    public static final MpoApplicationStatusType COMPLITE
        = new MpoApplicationStatusType(2, "Application Completed","ROLE_INSTEMP","","");

    public static final MpoApplicationStatusType APPROVEDBYINSTITUTE
        = new MpoApplicationStatusType(3, "Forwarded From Institute","ROLE_INSTITUTE","Declined by Institute","Institute");

    public static final MpoApplicationStatusType APPROVEDBYMANEGINGCOMMITTEE
        = new MpoApplicationStatusType(4, "Forwarded From Maneging Committee","ROLE_MANEGINGCOMMITTEE","Decline by Maneging Committee","Maneging Committee");

    public static final MpoApplicationStatusType APPROVEDBYDEO
        = new MpoApplicationStatusType(5, "Forwarded From District Education Officer","ROLE_DEO","Declined by DEO","District Education Officer");

    public static final MpoApplicationStatusType FRONTDESKLOOKUP
        = new MpoApplicationStatusType(6, "Forwarded From Front Desk","ROLE_FRONTDESK","Declined by DEO","MPO Front Desk");

    public static final MpoApplicationStatusType APPROVEDBYAD
        = new MpoApplicationStatusType(7, "Forwarded From Assistant Director","ROLE_AD","Decline by Assistant Director","Assistant Director");

    public static final MpoApplicationStatusType SUMMARIZEDBYAD
        = new MpoApplicationStatusType(8, "Forwarded From Assistant Director","ROLE_AD","Decline by Assistant Director","Assistant Director");

    public static final MpoApplicationStatusType APPROVEDBYDIRECTOR
        = new MpoApplicationStatusType(9, "Forwarded From Director","ROLE_DIRECTOR","Decline by Director","Director");

    public static final MpoApplicationStatusType APPROVEDBYDG
        = new MpoApplicationStatusType(10, "Forwarded From Director General","ROLE_DG","Decline by Director General","Director General");


    /*public static final MpoApplicationStatusType APPROVEDBYMPOCOMMITTEE
        = new MpoApplicationStatusType(10, "Approved By Director General","ROLE_MPOCOMMITTEE","Decline by Director General","MPO Committee");


    public static final MpoApplicationStatusType FINALLYAPPROVEDBYDG
        = new MpoApplicationStatusType(11, "Approved By Director General","ROLE_DG","Decline by Director General","Director General");*/

    private static final MpoApplicationStatusType[] list = {
        REJECTEED,
        DECLINED,
        COMPLITE,
        APPROVEDBYINSTITUTE,
        APPROVEDBYMANEGINGCOMMITTEE,
        APPROVEDBYDEO,
        FRONTDESKLOOKUP,
        APPROVEDBYAD,
        SUMMARIZEDBYAD,
        APPROVEDBYDIRECTOR,
        APPROVEDBYDG
       /* APPROVEDBYMPOCOMMITTEE,
        FINALLYAPPROVEDBYDG*/
    };
    private static final MpoApplicationStatusType[] getSchoolApproveList = {
        APPROVEDBYINSTITUTE,
        APPROVEDBYMANEGINGCOMMITTEE,
        APPROVEDBYDEO,
        FRONTDESKLOOKUP,
        APPROVEDBYAD,
        APPROVEDBYDIRECTOR,
        APPROVEDBYDG
    };
    private static final MpoApplicationStatusType[] getCollageApproveList = {
        APPROVEDBYINSTITUTE,
        APPROVEDBYMANEGINGCOMMITTEE,
        FRONTDESKLOOKUP,
        APPROVEDBYAD,
        APPROVEDBYDIRECTOR,
        APPROVEDBYDG

    };
    public static List<MpoApplicationStatusType> getMpoApproveList(String type,String role){
        List<MpoApplicationStatusType> mpoApplicationStatusTypes= new ArrayList<MpoApplicationStatusType>();
        if(type !=null && type.equals("BM")){
            for(MpoApplicationStatusType mpoApplicationStatusType:MpoApplicationStatusType.getCollageApproveList){
                if(!getMpoApplicationStatusTypeByRole(role).equals(mpoApplicationStatusType)){
                    mpoApplicationStatusTypes.add(mpoApplicationStatusType);
                }

            }
        }else{
            for(MpoApplicationStatusType mpoApplicationStatusType:MpoApplicationStatusType.getSchoolApproveList){
                if(!getMpoApplicationStatusTypeByRole(role).equals(mpoApplicationStatusType)){
                    mpoApplicationStatusTypes.add(mpoApplicationStatusType);
                }
            }
        }
        /*if(getMpoApplicationStatusTypeByRole(role).equals(getMpoApplicationStatusTypeByRole("ROLE_DG"))){
            mpoApplicationStatusTypes.add(getMpoApplicationStatusTypeByRole("ROLE_MPOCOMMITTEE"));
        }*/
        return mpoApplicationStatusTypes;
    }

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

    public String getRole() {
        return role;
    }

    public String getViewMessage() {
        return viewMessage;
    }

    public static MpoApplicationStatusType fromInt(int c) {
        if (c < 1 || c > TOTAL)
            throw new RuntimeException("Unknown code for fromInt in MpoApplicationStatusType");
        return list[c];
    }
    public static MpoApplicationStatusType nextMpoApplicationStatusType(int c) {
        if (c < 0 || c > TOTAL)
            throw new RuntimeException("Unknown code for fromInt in MpoApplicationStatusType");
        return list[c+1];
    }
    public static MpoApplicationStatusType prevMpoApplicationStatusType(int c) {
        if (c < 0 || c > TOTAL)
            throw new RuntimeException("Unknown code for fromInt in MpoApplicationStatusType");
        return list[c-1];
    }
    public static MpoApplicationStatusType previousRoleMpoApplicationStatusType(String role) {

        if(role==null){
            return null;
        }

        if(role.equals("")){
        return null;
        }
        else{
            return  prevMpoApplicationStatusType(getMpoApplicationStatusTypeByRole(role).getCode());
        }
    }

    public static MpoApplicationStatusType currentRoleMpoApplicationStatusType(String role) {

        if(role==null){
            return null;
        }

        if(role.equals("")){
            return null;
        }
        else{
            return  getMpoApplicationStatusTypeByRole(role);
        }
    }


    public static MpoApplicationStatusType getMpoApplicationStatusTypeByRole(String role){

        switch(role) {

            case "ROLE_INSTITUTE": return APPROVEDBYINSTITUTE;

            case "ROLE_MANEGINGCOMMITTEE": return APPROVEDBYMANEGINGCOMMITTEE;

            case "ROLE_DEO": return APPROVEDBYDEO;

            case "ROLE_FRONTDESK": return FRONTDESKLOOKUP;

            case "ROLE_AD": return APPROVEDBYAD;

            case "ROLE_DIRECTOR": return APPROVEDBYDIRECTOR;

            case "ROLE_DG": return APPROVEDBYDG;

            //case "ROLE_MPOCOMMITTEE": return APPROVEDBYMPOCOMMITTEE;

            default: return null;
            // etc...
        }

    }

    /*public static List getMpoApplicationStatusList() {
        return Arrays.asList(list);
    }*/
}
