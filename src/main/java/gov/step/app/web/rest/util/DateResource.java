package gov.step.app.web.rest.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by leads on 1/4/16.
 */
public class DateResource {
    public static LocalDate getDateAsLocalDate(){
        Date dates = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        Date input = new Date();
        return input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

    }
}
