package gov.step.app.service.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by yzaman on 12/23/15.
 */
public class MiscUtilities
{
    private final Logger log = LoggerFactory.getLogger(MiscUtilities.class);

    private final String ALPHA_CHARS_PREFIX = "0ABCDEFGHIJKLMNOPQRSTUVWXYZ789012";
    private final String ALPHA_CHARS_FLAT   = "ABCDEFGHIJKLMNOPQRSTUVWXYZ789012";
    private final String ALPHA_CHARS        = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcde";
    private final String ALPHA_CHARS2        = "0ABCDEFGHIJKLMNOPQRSTUVWXYZabcde";
    private final String ALPHA_CHARS_60     = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz34567890";

    public String getRandomFileName()
    {
        Calendar now = Calendar.getInstance();
        String timeSignature = Long.toString(now.getTimeInMillis());
        String randomSignature = Long.toString(Math.round(Math.random() * 100));
        return timeSignature + "_" + randomSignature;
    }

    public String getRandomUniqueId()
    {
        String uniqueCode = "";

        Calendar now = Calendar.getInstance();
        int currentYear2D = now.get(Calendar.YEAR) % 100;
        int currentMonth = now.get(Calendar.MONTH);
        int currentDay  = now.get(Calendar.DAY_OF_MONTH);
        int hours = now.get(Calendar.HOUR_OF_DAY);
        int minutes = now.get(Calendar.MINUTE);
        int seconds = now.get(Calendar.SECOND);
        uniqueCode += ALPHA_CHARS_PREFIX.charAt(currentYear2D);
        uniqueCode += ALPHA_CHARS_FLAT.charAt(currentMonth);
        uniqueCode += ALPHA_CHARS_PREFIX.charAt(currentDay);
        uniqueCode += hours+""+minutes+""+seconds;
        log.debug("unique: " + uniqueCode+" =>Y:" + currentYear2D + ",M: " + currentMonth + ",D:" + currentDay + ",H: " + hours + ",M:" + minutes + ",S:" + seconds);

        return uniqueCode;
    }

    public String getUniqueEmployeeId()
    {
        String uniqueCode = "";

        Calendar now = Calendar.getInstance();
        int currentYear = now.get(Calendar.YEAR);
        int currentMonth = now.get(Calendar.MONTH);
        int currentDay  = now.get(Calendar.DAY_OF_MONTH);
        int hours = now.get(Calendar.HOUR_OF_DAY);
        int minutes = now.get(Calendar.MINUTE);
        int seconds = now.get(Calendar.SECOND);
        uniqueCode += ""+currentYear;
        uniqueCode += ALPHA_CHARS.charAt(currentMonth);
        uniqueCode += ALPHA_CHARS2.charAt(currentDay);
        uniqueCode += ALPHA_CHARS.charAt(hours);
        uniqueCode += ALPHA_CHARS.charAt(minutes);
        uniqueCode += ALPHA_CHARS.charAt(seconds);
        log.debug("unique: " + uniqueCode+" =>Y:" + currentYear + ",M: " + currentMonth + ",D:" + currentDay + ",H: " + hours + ",M:" + minutes + ",S:" + seconds);

        return uniqueCode;
    }

    public String getUniqueEmployeeIdNumeric()
    {
        String uniqueCode = "";

        Calendar now = Calendar.getInstance();
        int currentYear = now.get(Calendar.YEAR);
        int currentMonth = now.get(Calendar.MONTH);
        int currentDay  = now.get(Calendar.DAY_OF_MONTH);
        int hours = now.get(Calendar.HOUR_OF_DAY);
        int minutes = now.get(Calendar.MINUTE);
        int seconds = now.get(Calendar.SECOND);
        uniqueCode += ""+currentYear;
        uniqueCode += ALPHA_CHARS.charAt(currentMonth);
        uniqueCode += ALPHA_CHARS2.charAt(currentDay);
        uniqueCode += hours+""+minutes+""+seconds;
        log.debug("unique: " + uniqueCode+" =>Y:" +currentYear+ ",M: " + currentMonth + ",D:" + currentDay + ",H: " + hours + ",M:" + minutes + ",S:" + seconds);

        return uniqueCode;
    }



}
