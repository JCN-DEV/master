package gov.step.app.web.rest.dto;

import gov.step.app.domain.*;
import gov.step.app.service.util.InstEmployeeInfo;

import java.util.List;

/**
 * Created by rana on 12/23/15.
 */
public class InstituteDto {

    private InstGenInfo instGenInfo;

    private InstAdmInfo instAdmInfo;

    private InsAcademicInfo insAcademicInfo;

    private InstInfraInfo instInfraInfo;

    private InstEmpCount instEmpCount;

    private List<InstFinancialInfo> instFinancialInfo;

    private List<InstGovernBody> instGovernBody;

    private List<IisCurriculumInfo> instCurriculums;

    private List<IisCourseInfo> instCourses;


    public InstGenInfo getInstGenInfo() {
        return instGenInfo;
    }

    public void setInstGenInfo(InstGenInfo instGenInfo) {
        this.instGenInfo = instGenInfo;
    }

    public InstAdmInfo getInstAdmInfo() {
        return instAdmInfo;
    }

    public void setInstAdmInfo(InstAdmInfo instAdmInfo) {
        this.instAdmInfo = instAdmInfo;
    }

    public InsAcademicInfo getInsAcademicInfo() {
        return insAcademicInfo;
    }

    public void setInsAcademicInfo(InsAcademicInfo insAcademicInfo) {
        this.insAcademicInfo = insAcademicInfo;
    }

    public InstInfraInfo getInstInfraInfo() {
        return instInfraInfo;
    }

    public void setInstInfraInfo(InstInfraInfo instInfraInfo) {
        this.instInfraInfo = instInfraInfo;
    }

    public InstEmpCount getInstEmpCount() {
        return instEmpCount;
    }

    public void setInstEmpCount(InstEmpCount instEmpCount) {
        this.instEmpCount = instEmpCount;
    }

    public List<InstFinancialInfo> getInstFinancialInfo() {
        return instFinancialInfo;
    }

    public void setInstFinancialInfo(List<InstFinancialInfo> instFinancialInfo) {
        this.instFinancialInfo = instFinancialInfo;
    }

    public List<InstGovernBody> getInstGovernBody() {
        return instGovernBody;
    }

    public void setInstGovernBody(List<InstGovernBody> instGovernBody) {
        this.instGovernBody = instGovernBody;
    }

    public List<IisCurriculumInfo> getInstCurriculums() {
        return instCurriculums;
    }

    public void setInstCurriculums(List<IisCurriculumInfo> instCurriculums) {
        this.instCurriculums = instCurriculums;
    }

    public List<IisCourseInfo> getInstCourses() {
        return instCourses;
    }

    public void setInstCourses(List<IisCourseInfo> instCourses) {
        this.instCourses = instCourses;
    }
}
