package gov.step.app.domain.enumeration;

import java.util.Arrays;
import java.util.List;

/**
 * Created by leads on 12/10/15.
 */
public class LocalityType {

    private final int id;
    private final String code;

    public static final int TOTAL = 3;

    private LocalityType(int id, String code) {
       this.id=id;
        this.code = code;

    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("id = [");
        buf.append(this.code);
        buf.append("code = [");
        buf.append(this.code);
        return buf.toString();
    }

    public static final LocalityType CITY_CORPORATION
        = new LocalityType(1, "City Corporation ");

    public static final LocalityType MUNICIPALITY
        = new LocalityType(2, "Municipality");
    public static final LocalityType OTHERS
        = new LocalityType(3, "Others");



    private static final LocalityType[] list = {
        CITY_CORPORATION,
        MUNICIPALITY,
        OTHERS

    };

    public int toInt() {
        return this.id;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return this.code;
    }

    public static LocalityType fromInt(int c) {
        if (c < 1 || c > TOTAL)
            throw new RuntimeException("Unknown code for fromInt in Locality");
        return list[c];
    }


    public static List getLocalityTypeList() {
        return Arrays.asList(list);
    }
}
