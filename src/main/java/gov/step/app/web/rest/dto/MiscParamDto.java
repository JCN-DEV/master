package gov.step.app.web.rest.dto;

import java.time.LocalDate;

/**
 * Created by yzaman on 9/25/16.
 */
public class MiscParamDto
{
    private String dataType;
    private LocalDate minDate;
    private LocalDate maxDate;

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public LocalDate getMinDate() {
        return minDate;
    }

    public void setMinDate(LocalDate minDate) {
        this.minDate = minDate;
    }

    public LocalDate getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(LocalDate maxDate) {
        this.maxDate = maxDate;
    }
}
