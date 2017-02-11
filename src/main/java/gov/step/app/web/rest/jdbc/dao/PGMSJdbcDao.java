package gov.step.app.web.rest.jdbc.dao;

/**
 * Created by ism on 4/16/16.
 */

import gov.step.app.domain.PgmsPenGrRate;
import gov.step.app.domain.HrEmployeeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

//import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;
import java.sql.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class PGMSJdbcDao
{
    private final Logger logger = LoggerFactory.getLogger(PGMSJdbcDao.class);

    private final JdbcTemplate jdbcTemplate;

    private final String SQL_SELECT_ALL			= "SELECT * FROM VW_ADM_APPR_REJ_DASHBOARD";

    private final String SQL_SELECT_BY_VERSION_YEAR = "select pgr.* from pgms_pen_gr_rate pgr, pgms_pen_gr_setup pgs Where pgs.id = pgr.pen_gr_set_id AND (pgs.setup_version = ? OR pgs.setup_version = ? ) AND pgr.working_year = ?";
    private final String SQL_SELECT_BY_RETIREMENT_YEAR = "select * from hr_employee_info hrInfo where hrInfo.id NOT IN (select emp_id from pgms_notification)";
    private final String SQL_SELECT_BY_RETIREMENT = "SELECT hrinfo.* FROM HR_EMPLOYEE_INFO hrinfo WHERE hrinfo.ID NOT IN (select pgmsn.EMP_ID from PGMS_NOTIFICATION pgmsn)" +
        " and hrinfo.RETIREMENT_DATE BETWEEN TO_DATE(?, 'yyyy-MON-dd') AND TO_DATE(?, 'yyyy-MON-dd')";

    private final String SQL_SELECT_EMP_DESIG_COUNT = "SELECT COUNT(*) as TotalDesig FROM HR_EMPLOYEE_INFO WHERE DESIGNATION_INFO_ID = ? ";
    private final String SQL_SELECT_EMP_BY_FILTER   = "SELECT * FROM HR_EMPLOYEE_INFO WHERE ? LIKE ? ";
    private final String SQL_SELECT_ALL_EMP         = "SELECT * FROM HR_EMPLOYEE_INFO";

    @Autowired
    public PGMSJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    public List<PgmsPenGrRate> findPensionRateByVersionAndYear(String versionNumber, String versionNumber1, long workingYear)
    {
        logger.info("findPensionRateByVersionAndYear by field: {}, value: {}", versionNumber, versionNumber1, workingYear);
        List<PgmsPenGrRate> pgmsRateInfoList = null;
        //List<PgmsPenGrRate> rateInfo = null;
        try
        {
            //setJdbcTemplate();
            pgmsRateInfoList  = jdbcTemplate.query(SQL_SELECT_BY_VERSION_YEAR,
                new Object[] {versionNumber, versionNumber1, workingYear},
                new BeanPropertyRowMapper<PgmsPenGrRate>(PgmsPenGrRate.class));
            //if(pgmsRateInfoList != null)
            //    rateInfo = pgmsRateInfoList;
        }
        catch(Exception ex)
        {
            logger.error("findPensionRateByVersionAndYear Msg: "+ex.getMessage());
            //throw new DAOException(ExceptionTypes.DB_QUERY_PROCESSING_FAILED, "find all failed ExpMsg: "+ex.getMessage(),ex);
        }
        return pgmsRateInfoList;
    }

    public List<HrEmployeeInfo> findAllByBetweenRetirementDate(String startDate, String endDate)
    {
        logger.info("findAllByBetweenRetirementDate by field:"+SQL_SELECT_BY_RETIREMENT);
        List<HrEmployeeInfo> hrEmpInfoList = null;
        //List<PgmsPenGrRate> rateInfo = null;
        try
        {
            //setJdbcTemplate();
            hrEmpInfoList  = jdbcTemplate.query(SQL_SELECT_BY_RETIREMENT,
                new Object[] {startDate, endDate},
                new BeanPropertyRowMapper<HrEmployeeInfo>(HrEmployeeInfo.class));
            //if(pgmsRateInfoList != null)
            //    rateInfo = pgmsRateInfoList;
        }
        catch(Exception ex)
        {
            logger.error("findAllByRetirementDate Msg: "+ex.getMessage());
            //throw new DAOException(ExceptionTypes.DB_QUERY_PROCESSING_FAILED, "find all failed ExpMsg: "+ex.getMessage(),ex);
        }
        return hrEmpInfoList;
    }

    /*public List<HrEmployeeInfo> findAll()
    {
        logger.info("findAll");
        List<HrEmployeeInfo> hrEmpInfoList = null;

        try
        {

            List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_ALL_EMP);

            for (Map row : rows)
            {
                HrEmployeeInfo empInfo = new HrEmployeeInfo();
                empInfo.setId((Long)(row.get("ID")));
                empInfo.setFullName((String)(row.get("FULL_NAME")));
                empInfo.setEmailAddress((String)(row.get("EMAIL_ADDRESS")));
                empInfo.setBirthDate((LocalDate) (row.get("BIRTH_DATE")));

                hrEmpInfoList.add(empInfo);

                logger.info("EmpInfo: "+empInfo.getFullName() +", Email: "+empInfo.getEmailAddress()+", Birthday: "+empInfo.getBirthDate());
            }

        }
        catch(Exception ex)
        {
            logger.error("findAll Msg: "+ex.getMessage());
        }
        return hrEmpInfoList;
    }*/
}
