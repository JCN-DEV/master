package gov.step.app.web.rest.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by leads on 1/4/16.
 */
public class TransactionIdResource {

    public String getGeneratedid(String prefixOfId){

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        long timeMilli = date.getTime();
        String generatedId = prefixOfId+timeMilli;
        return generatedId;
    }
}
