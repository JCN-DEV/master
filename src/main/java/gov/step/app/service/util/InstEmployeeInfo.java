package gov.step.app.service.util;

import gov.step.app.domain.*;

import java.util.List;
import java.util.Map;

/**
 * Created by rana on 12/9/15.
 */
public class InstEmployeeInfo {
    private InstEmployee instEmployee;

    private InstEmpAddress instEmpAddress;

    private InstEmplRecruitInfo instEmplRecruitInfo;

    private InstEmplBankInfo instEmplBankInfo;

    private InstEmpSpouseInfo instEmpSpouseInfo;

    private List<InstEmplTraining> instEmplTrainings;

    private List<InstEmpEduQuali> instEmpEduQualis;

    private List<InstEmplExperience> instEmplExperiences;

    List<Map<String,Object>> salaryMap;

    public InstEmployee getInstEmployee() {
        return instEmployee;
    }

    public void setInstEmployee(InstEmployee instEmployee) {
        this.instEmployee = instEmployee;
    }

    public InstEmpAddress getInstEmpAddress() {
        return instEmpAddress;
    }

    public InstEmplRecruitInfo getInstEmplRecruitInfo() {
        return instEmplRecruitInfo;
    }

    public void setInstEmplRecruitInfo(InstEmplRecruitInfo instEmplRecruitInfo) {
        this.instEmplRecruitInfo = instEmplRecruitInfo;
    }

    public InstEmpSpouseInfo getInstEmpSpouseInfo() {
        return instEmpSpouseInfo;
    }

    public void setInstEmpSpouseInfo(InstEmpSpouseInfo instEmpSpouseInfo) {
        this.instEmpSpouseInfo = instEmpSpouseInfo;
    }

    public InstEmplBankInfo getInstEmplBankInfo() {
        return instEmplBankInfo;
    }

    public void setInstEmplBankInfo(InstEmplBankInfo instEmplBankInfo) {
        this.instEmplBankInfo = instEmplBankInfo;
    }

    public void setInstEmpAddress(InstEmpAddress instEmpAddress) {
        this.instEmpAddress = instEmpAddress;
    }

    public List<InstEmplTraining> getInstEmplTrainings() {
        return instEmplTrainings;
    }

    public void setInstEmplTrainings(List<InstEmplTraining> instEmplTrainings) {
        this.instEmplTrainings = instEmplTrainings;
    }

    public List<InstEmpEduQuali> getInstEmpEduQualis() {
        return instEmpEduQualis;
    }

    public void setInstEmpEduQualis(List<InstEmpEduQuali> instEmpEduQualis) {
        this.instEmpEduQualis = instEmpEduQualis;
    }

    public List<InstEmplExperience> getInstEmplExperiences() {
        return instEmplExperiences;
    }

    public void setInstEmplExperiences(List<InstEmplExperience> instEmplExperiences) {
        this.instEmplExperiences = instEmplExperiences;
    }

    public List<Map<String,Object>> getSalaryMap() {
        return salaryMap;
    }

    public void setSalaryMap(List<Map<String,Object>> salaryMap) {
        this.salaryMap = salaryMap;
    }
}
