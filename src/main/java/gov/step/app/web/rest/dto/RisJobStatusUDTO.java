package gov.step.app.web.rest.dto;

/**
 * Created by shafiqur on 6/19/16.
 */
public class RisJobStatusUDTO {


    String CircularNo;
    Integer status;
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public String getCircularNo() {
        return CircularNo;
    }

    public void setCircularNo(String circularNo) {
        CircularNo = circularNo;
    }

    @Override
    public String toString() {
        return "RisJobStatusUDTO{" +
            "CircularNo='" + CircularNo + '\'' +
            ", status=" + status +
            '}';
    }

}
