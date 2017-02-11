package gov.step.app.web.rest.dto;

import gov.step.app.domain.payroll.PrlSalaryAllowDeducInfo;

import java.math.BigDecimal;

/**
 * Created by yzaman on 6/5/16.
 */
public class PrlSalaryStructureDto
{
    private Boolean fixedBasic;
    private BigDecimal basicMaximum;
    private BigDecimal basicMinimum;
    private String allowDeducType;
    private PrlSalaryAllowDeducInfo salaryAllowDeducInfo;

    public BigDecimal getBasicMaximum() {
        return basicMaximum;
    }

    public void setBasicMaximum(BigDecimal basicMaximum) {
        this.basicMaximum = basicMaximum;
    }

    public BigDecimal getBasicMinimum() {
        return basicMinimum;
    }

    public void setBasicMinimum(BigDecimal basicMinimum) {
        this.basicMinimum = basicMinimum;
    }

    public String getAllowDeducType() {
        return allowDeducType;
    }

    public void setAllowDeducType(String allowDeducType) {
        this.allowDeducType = allowDeducType;
    }

    public PrlSalaryAllowDeducInfo getSalaryAllowDeducInfo() {
        return salaryAllowDeducInfo;
    }

    public void setSalaryAllowDeducInfo(PrlSalaryAllowDeducInfo salaryAllowDeducInfo) {this.salaryAllowDeducInfo = salaryAllowDeducInfo;}

    public Boolean getFixedBasic() {return fixedBasic;}

    public void setFixedBasic(Boolean fixedBasic) {this.fixedBasic = fixedBasic;}
}
