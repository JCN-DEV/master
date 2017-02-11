package gov.step.app.web.rest.dto;

/**
 * Created by yzaman on 7/23/16.
 */
public class PrlSalaryGenerationDto
{
    private String monthName;
    private Long year;
    private Long createBy;
    private String salaryType;

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public String getSalaryType() {
        return salaryType;
    }

    public void setSalaryType(String salaryType) {
        this.salaryType = salaryType;
    }
}
