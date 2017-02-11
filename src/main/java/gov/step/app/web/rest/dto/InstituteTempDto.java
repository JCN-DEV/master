package gov.step.app.web.rest.dto;

import gov.step.app.domain.*;

import java.util.List;

/**
 * Created by rana on 12/23/15.
 */
public class InstituteTempDto {

    private InstGenInfo instGenInfo;

    private InstAdmInfoTemp instAdmInfo;

    private InsAcademicInfoTemp insAcademicInfo;

    private InstInfraInfoTemp instInfraInfo;

    private InstEmpCount instEmpCount;

    private List<InstFinancialInfoTemp> instFinancialInfo;

    private List<InstGovernBodyTemp> instGovernBody;

    private List<IisCurriculumInfoTemp> instCurriculums;

    private List<IisCourseInfoTemp> instCourses;


    public InstGenInfo getInstGenInfo() {
        return instGenInfo;
    }

    public void setInstGenInfo(InstGenInfo instGenInfo) {
        this.instGenInfo = instGenInfo;
    }

    public InstAdmInfoTemp getInstAdmInfo() {
        return instAdmInfo;
    }

    public void setInstAdmInfo(InstAdmInfoTemp instAdmInfo) {
        this.instAdmInfo = instAdmInfo;
    }

    public InsAcademicInfoTemp getInsAcademicInfo() {
        return insAcademicInfo;
    }

    public void setInsAcademicInfo(InsAcademicInfoTemp insAcademicInfo) {
        this.insAcademicInfo = insAcademicInfo;
    }

    public InstInfraInfoTemp getInstInfraInfo() {
        return instInfraInfo;
    }

    public void setInstInfraInfo(InstInfraInfoTemp instInfraInfo) {
        this.instInfraInfo = instInfraInfo;
    }

    public InstEmpCount getInstEmpCount() {
        return instEmpCount;
    }

    public void setInstEmpCount(InstEmpCount instEmpCount) {
        this.instEmpCount = instEmpCount;
    }

    public List<InstFinancialInfoTemp> getInstFinancialInfo() {
        return instFinancialInfo;
    }

    public void setInstFinancialInfo(List<InstFinancialInfoTemp> instFinancialInfo) {
        this.instFinancialInfo = instFinancialInfo;
    }

    public List<InstGovernBodyTemp> getInstGovernBody() {
        return instGovernBody;
    }

    public void setInstGovernBody(List<InstGovernBodyTemp> instGovernBody) {
        this.instGovernBody = instGovernBody;
    }

    public List<IisCurriculumInfoTemp> getInstCurriculums() {
        return instCurriculums;
    }

    public void setInstCurriculums(List<IisCurriculumInfoTemp> instCurriculums) {
        this.instCurriculums = instCurriculums;
    }

    public List<IisCourseInfoTemp> getInstCourses() {
        return instCourses;
    }

    public void setInstCourses(List<IisCourseInfoTemp> instCourses) {
        this.instCourses = instCourses;
    }
}
