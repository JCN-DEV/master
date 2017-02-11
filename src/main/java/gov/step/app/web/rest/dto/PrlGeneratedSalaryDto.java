package gov.step.app.web.rest.dto;

import gov.step.app.domain.payroll.PrlEmpGenSalDetailInfo;
import gov.step.app.domain.payroll.PrlEmpGeneratedSalInfo;
import gov.step.app.domain.payroll.PrlGeneratedSalaryInfo;

import java.util.List;

/**
 * Created by yzaman on 7/12/16.
 */
public class PrlGeneratedSalaryDto
{
    private PrlGeneratedSalaryInfo salaryInfo;
    private PrlEmpGeneratedSalInfo employeeSalaryInfo;
    private List<PrlEmpGenSalDetailInfo> salaryDetailList;

    public PrlGeneratedSalaryInfo getSalaryInfo() {
        return salaryInfo;
    }

    public void setSalaryInfo(PrlGeneratedSalaryInfo salaryInfo) {
        this.salaryInfo = salaryInfo;
    }

    public PrlEmpGeneratedSalInfo getEmployeeSalaryInfo() {
        return employeeSalaryInfo;
    }

    public void setEmployeeSalaryInfo(PrlEmpGeneratedSalInfo employeeSalaryInfo) {
        this.employeeSalaryInfo = employeeSalaryInfo;
    }

    public List<PrlEmpGenSalDetailInfo> getSalaryDetailList() {
        return salaryDetailList;
    }

    public void setSalaryDetailList(List<PrlEmpGenSalDetailInfo> salaryDetailList) {
        this.salaryDetailList = salaryDetailList;
    }
}
