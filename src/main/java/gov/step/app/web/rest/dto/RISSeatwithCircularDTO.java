package gov.step.app.web.rest.dto;

/**
 * Created by shafiqur on 6/18/16.
 */
public class RISSeatwithCircularDTO {


    Long Seat;
    String CircularNo;
    public String getCircularNo() {
        return CircularNo;
    }

    public void setCircularNo(String circularNo) {
        CircularNo = circularNo;
    }

    public Long getSeat() {
        return Seat;
    }

    public void setSeat(Long seat) {
        Seat = seat;
    }

    @Override
    public String toString() {
        return "RISSeatwithCircularDTO{" +
            "Seat=" + Seat +
            ", CircularNo='" + CircularNo + '\'' +
            '}';
    }


}
