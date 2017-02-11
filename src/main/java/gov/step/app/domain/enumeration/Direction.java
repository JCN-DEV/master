package gov.step.app.domain.enumeration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by leads on 12/10/15.
 */
public class Direction  {

    private final int id;
    private final String code;

    public static final int TOTAL = 4;

    private Direction(int id, String code) {
       this.id=id;
       this.code = code;

    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("id = [");
        buf.append(this.id);
        buf.append("code = [");
        buf.append(this.code);
        return buf.toString();
    }

    public static final Direction NORTH
        = new Direction(1, "North");

    public static final Direction SOUTH
        = new Direction(2, "South");
    public static final Direction EAST
        = new Direction(3, "East");
    public static final Direction WEST
        = new Direction(4, "West");



    private static final Direction[] list = {
        NORTH,
        SOUTH,
        EAST,
        WEST
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

    public static Direction fromInt(int c) {
        if (c < 1 || c > TOTAL)
            throw new RuntimeException("Unknown code for fromInt in Locality");
        return list[c];
    }


    public static List getDirectionList() {
        return Arrays.asList(list);
    }
}
