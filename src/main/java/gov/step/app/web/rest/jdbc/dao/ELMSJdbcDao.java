package gov.step.app.web.rest.jdbc.dao;


import gov.step.app.domain.EmployeeLoanRequisitionForm;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.common.inject.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by bappimazumder on 10/5/16.
 */
@Component
public class ELMSJdbcDao {

    private final Logger logger = LoggerFactory.getLogger(ELMSJdbcDao.class);

    private final JdbcTemplate jdbcTemplate;

    // Query String
    private final String SQL_SELECT_LOANREQUISITION_BY_INSTITUTE = "SELECT loanType.LOAN_TYPE_NAME, rulesSetup.LOAN_NAME, loanReq.ID, loanReq.LOAN_REQUISITION_CODE, loanReq.AMOUNT,hrInfo.EMPLOYEE_ID, hrInfo.FULL_NAME" +
                                                                    "  FROM ELMS_LOAN_TYPE_SETUP loanType,ELMS_LOAN_RULES_SETUP rulesSetup,ELMS_LOAN_REQUSITION_FORM loanReq" +
                                                                    "       ,HR_EMPLOYEE_INFO hrInfo ,INSTITUTE inst" +
                                                                    "  WHERE loanType.ID = loanReq.EMPLOYEELOANTYPESETUP_ID AND rulesSetup.ID = loanReq.EMPLOYEELOANRULESSETUP_ID AND" +
                                                                    "        loanReq.EMPLOYEE_INFO_ID = hrInfo.ID AND inst.ID = hrInfo.INSTITUTE_ID AND" +
                                                                    "        inst.ID = ? AND loanReq.STATUS = ? AND loanReq.APPROVE_STATUS = ? AND loanReq.APPLY_TYPE = ?";
//
//    private final String SQL_SELECT_LOAN_REQUISITION_BY_APPROVE_STATUS = " SELECT loanType.LOAN_TYPE_NAME, rulesSetup.LOAN_NAME, loanReq.ID,loanReq.LOAN_REQUISITION_CODE,loanReq.AMOUNT, hrInfo.EMPLOYEE_ID, hrInfo.FULL_NAME" +
//                                                                        "  FROM ELMS_LOAN_TYPE_SETUP loanType,ELMS_LOAN_RULES_SETUP rulesSetup,ELMS_LOAN_REQUSITION_FORM loanReq,HR_EMPLOYEE_INFO hrInfo" +
//                                                                        "  WHERE loanType.ID = loanReq.EMPLOYEELOANTYPESETUP_ID AND rulesSetup.ID = loanReq.EMPLOYEELOANRULESSETUP_ID AND" +
//                                                                        "        loanReq.EMPLOYEE_INFO_ID = hrInfo.ID AND loanReq.STATUS = ? AND loanReq.APPROVE_STATUS IN(?) ";

    @Autowired
    public ELMSJdbcDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    // Get Loan RequisitionData By institute ID
    public List<Map<String, Object>> getEmployeeLoanRequisitionDataByInstitute(Long instituteId, Integer approveStatus,Integer activeStatus, String applyType ){
        logger.info(" Execute getEmployeeLoanRequisitionDataByInstitute ");

        List<Map<String, Object>> requisitionFormList = null;
        try {
            requisitionFormList = jdbcTemplate.queryForList(SQL_SELECT_LOANREQUISITION_BY_INSTITUTE,instituteId, activeStatus, approveStatus, applyType);
        }catch (Exception ex){
            logger.error("getEmployeeLoanRequisitionDataByInstitute Msg: "+ex.getMessage());
        }
        return  requisitionFormList;
    }

    // Get Loan Requisition Data By ApproveStatus
    public List<Map<String,Object>> getEmployeeLoanRequisitionDataByApproveStatus(Integer activeStatus ,String applyType, List<Integer> approveStatus){
        logger.info(" Execute getEmployeeLoanRequisitionDataByApproveStatus ");
        List<Map<String, Object>> requisitionFormList = null;
        try {
        String  arrayApproveStatus = StringUtils.join(approveStatus, ',');
        String SQL_SELECT_LOAN_REQUISITION_BY_APPROVE_STATUS = " SELECT loanType.LOAN_TYPE_NAME, rulesSetup.LOAN_NAME, loanReq.ID,loanReq.LOAN_REQUISITION_CODE,loanReq.AMOUNT, hrInfo.EMPLOYEE_ID, hrInfo.FULL_NAME" +
                                                                "  FROM ELMS_LOAN_TYPE_SETUP loanType,ELMS_LOAN_RULES_SETUP rulesSetup,ELMS_LOAN_REQUSITION_FORM loanReq,HR_EMPLOYEE_INFO hrInfo" +
                                                                "  WHERE loanType.ID = loanReq.EMPLOYEELOANTYPESETUP_ID AND rulesSetup.ID = loanReq.EMPLOYEELOANRULESSETUP_ID AND" +
                                                                "        loanReq.EMPLOYEE_INFO_ID = hrInfo.ID AND loanReq.STATUS = ? AND loanReq.APPLY_TYPE = ? AND loanReq.APPROVE_STATUS IN("+arrayApproveStatus+") ";

            System.out.println("-----------------------------------");
            System.out.println(SQL_SELECT_LOAN_REQUISITION_BY_APPROVE_STATUS);

            requisitionFormList = jdbcTemplate.queryForList(SQL_SELECT_LOAN_REQUISITION_BY_APPROVE_STATUS,activeStatus,applyType);

        }catch (Exception ex){
            logger.error("getEmployeeLoanRequisitionDataByApproveStatus Msg: "+ex.getMessage());
        }
        return  requisitionFormList;
    }

}
