package gov.step.app.web.rest.dto;

/**
 * Created by shafiqur on 6/18/16.
 */
public class RisCircularNo {


    String CircularNo;
    public String getCircularNo() {
        return CircularNo;
    }

    public void setCircularNo(String circularNo) {
        CircularNo = circularNo;
    }

    @Override
    public String toString() {
        return "RisCircularNo{" +
            "CircularNo='" + CircularNo + '\'' +
            '}';
    }
}
