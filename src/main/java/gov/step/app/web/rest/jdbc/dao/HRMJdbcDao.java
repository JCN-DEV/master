package gov.step.app.web.rest.jdbc.dao;

/**
 * Created by yzaman on 3/8/16.
 */


import gov.step.app.domain.HrEmployeeInfo;
import gov.step.app.web.rest.dto.HrmDashboardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class HRMJdbcDao
{
    private final Logger logger = LoggerFactory.getLogger(HRMJdbcDao.class);

    private final JdbcTemplate jdbcTemplate;

    private final String SQL_SELECT_ALL			= "SELECT * FROM VW_ADM_APPR_REJ_DASHBOARD";
    private final String SQL_SELECT_EMP_DEPT_DESIG_COUNT = "SELECT COUNT(*) as TotalDesig FROM HR_EMPLOYEE_INFO WHERE DEPARTMENT_INFO_ID = ? AND DESIGNATION_INFO_ID = ? ";
    private final String SQL_SELECT_EMP_DESIG_COUNT = "SELECT COUNT(*) as TotalDesig FROM HR_EMPLOYEE_INFO WHERE DESIGNATION_INFO_ID = ? ";
    private final String SQL_SELECT_EMP_DESIG_COUNT_BYORG = "SELECT COUNT(*) as TotalDesig FROM HR_EMPLOYEE_INFO WHERE DESIGNATION_INFO_ID = ? AND WORK_AREA_DTL_ID = ? ";
    private final String SQL_SELECT_EMP_DESIG_COUNT_BYINST = "SELECT COUNT(*) as TotalDesig FROM HR_EMPLOYEE_INFO WHERE DESIGNATION_INFO_ID = ? AND INSTITUTE_ID = ? ";
    private final String SQL_SELECT_EMP_BY_FILTER   = "SELECT * FROM HR_EMPLOYEE_INFO WHERE ? LIKE ? ";
    private final String SQL_SELECT_ALL_EMP         = "SELECT * FROM HR_EMPLOYEE_INFO";

    @Autowired
    public HRMJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<HrmDashboardDto> findApproveRejectedList()
    {
        logger.info("findApproveList from view");
        List<HrmDashboardDto> approvedRejectedList = null;
        try
        {
            //setJdbcTemplate();
            approvedRejectedList  = jdbcTemplate.query(SQL_SELECT_ALL, new BeanPropertyRowMapper<HrmDashboardDto>(HrmDashboardDto.class));
        }
        catch(Exception ex)
        {
            logger.error("findApproveRejectedList Msg: "+ex.getMessage());
            //throw new DAOException(ExceptionTypes.DB_QUERY_PROCESSING_FAILED, "find all failed ExpMsg: "+ex.getMessage(),ex);
        }
        return approvedRejectedList;
    }

    public int getEmployeeWiseDesignationCount(Long deptId, Long desigId)
    {
        int count = 0;
        try
        {
            count = jdbcTemplate.queryForObject(SQL_SELECT_EMP_DEPT_DESIG_COUNT, new Object[] { deptId, desigId }, Integer.class);
        }
        catch(Exception ex)
        {
            logger.error("getEmployeeWiseDesignationCount Msg: "+ex.getMessage());
        }
        return count;
    }

    public int getEmployeeWiseDesignationCount(Long desigId)
    {
        int count = 0;
        try
        {
            count = jdbcTemplate.queryForObject(SQL_SELECT_EMP_DESIG_COUNT, new Object[] {desigId }, Integer.class);
        }
        catch(Exception ex)
        {
            logger.error("getEmployeeWiseDesignationCount Msg: "+ex.getMessage());
        }
        return count;
    }

    public int getEmployeeWiseDesignationCountByOrganization(Long desigId, Long orgId)
    {
        int count = 0;
        try
        {
            count = jdbcTemplate.queryForObject(SQL_SELECT_EMP_DESIG_COUNT_BYORG, new Object[] {desigId, orgId}, Integer.class);
        }
        catch(Exception ex)
        {
            logger.error("getEmployeeWiseDesignationCountByOrganization Msg: "+ex.getMessage());
        }
        return count;
    }

    public int getEmployeeWiseDesignationCountByInstitute(Long desigId, Long instId)
    {
        int count = 0;
        try
        {
            count = jdbcTemplate.queryForObject(SQL_SELECT_EMP_DESIG_COUNT_BYINST, new Object[] {desigId, instId}, Integer.class);
        }
        catch(Exception ex)
        {
            logger.error("getEmployeeWiseDesignationCountByInstitute Msg: "+ex.getMessage());
        }
        return count;
    }

    public List<HrEmployeeInfo> findAllEmployeeByFilter(String fieldName, String fieldValue)
    {
        logger.info("findAllEmployeeByFilter by field: {}, value: {}", fieldName, fieldValue);
        List<HrEmployeeInfo> employeeList = null;
        try
        {
            //setJdbcTemplate();
            employeeList  = jdbcTemplate.query(SQL_SELECT_EMP_BY_FILTER,
                new String[] {fieldName, fieldValue},
                new BeanPropertyRowMapper<HrEmployeeInfo>(HrEmployeeInfo.class));
        }
        catch(Exception ex)
        {
            logger.error("findAllEmployeeByFilter Msg: "+ex.getMessage());
            //throw new DAOException(ExceptionTypes.DB_QUERY_PROCESSING_FAILED, "find all failed ExpMsg: "+ex.getMessage(),ex);
        }
        return employeeList;
    }

    public List<HrEmployeeInfo> findAllEmployeeList()
    {
        logger.info("findAllEmployeeList");
        List<HrEmployeeInfo> employeeList = null;
        try
        {
            //setJdbcTemplate();
            employeeList  = jdbcTemplate.query(SQL_SELECT_ALL_EMP,
                new BeanPropertyRowMapper<HrEmployeeInfo>(HrEmployeeInfo.class));
        }
        catch(Exception ex)
        {
            logger.error("findAllEmployeeByFilter Msg: "+ex.getMessage());
            //throw new DAOException(ExceptionTypes.DB_QUERY_PROCESSING_FAILED, "find all failed ExpMsg: "+ex.getMessage(),ex);
        }
        return employeeList;
    }

}
