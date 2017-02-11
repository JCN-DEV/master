package gov.step.app.web.rest.jdbc.dao;

import gov.step.app.domain.enumeration.AllowanceDeductionType;
import gov.step.app.domain.payroll.*;
import gov.step.app.service.constnt.PayrollManagementConstant;
import gov.step.app.web.rest.dto.PrlSalaryGenerationDto;
import gov.step.app.web.rest.dto.PrlSalaryStructureDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yzaman on 5/31/16.
 */
@Component
public class PayrollJdbcDao
{
    private final Logger logger = LoggerFactory.getLogger(PayrollJdbcDao.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PayrollJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final String SQL_SELECT_ALLOW_BY_SCALE_AND_GRADE			= "SELECT pal.ID, pal.BASIC_MINIMUM, pal.BASIC_MAXIMUM, pal.FIXED_BASIC, ? as PAYSCALE_INFO_ID, " +
        "aldd.ID as ALLOWANCE_INFO_ID, pal.CREATE_BY, pal.CREATE_DATE, pal.UPDATE_BY, pal.UPDATE_DATE, pal.ACTIVE_STATUS, " +
        "aldd.NAME, aldd.DESCRIPTION,aldd.ALLOW_DEDUC_TYPE  " +
        "FROM PRL_PAYSCALE_ALLOWANCE_INFO pal RIGHT OUTER JOIN " +
        "(SELECT aldd.* FROM PRL_ALLOW_DEDUCT_INFO aldd, PRL_ALLOWDEDUCT_GRADE_MAP alddmp " +
        "WHERE aldd.ID = alddmp.PRL_ALLOW_DEDUCT_INFOS_ID AND aldd.ALLOW_DEDUC_TYPE like '"+AllowanceDeductionType.Allowance+"' AND alddmp.GRADE_INFOS_ID = ?) aldd " +
        "ON (pal.ALLOWANCE_INFO_ID = aldd.ID AND pal.PAYSCALE_INFO_ID=?)";


    //SELECT * FROM PRL_PAYSCALE_ALLOWANCE_INFO pal RIGHT OUTER JOIN
    // (SELECT aldd.* FROM PRL_ALLOW_DEDUCT_INFO aldd, PRL_ALLOWDEDUCT_GRADE_MAP alddmp WHERE aldd.ID = alddmp.PRL_ALLOW_DEDUCT_INFOS_ID AND alddmp.GRADE_INFOS_ID = 5957) aldd
    // ON (pal.ALLOWANCE_INFO_ID = aldd.ID AND pal.PAYSCALE_INFO_ID=10458) ;

    public List<PrlPayscaleAllowanceInfo> findAllPayscaleAllowanceByPayScaleAndGrade(Long payscaleId, Long gradeId)
    {
        logger.info("find allowance by grades using left join query: "+SQL_SELECT_ALLOW_BY_SCALE_AND_GRADE);
        List<PrlPayscaleAllowanceInfo> psclAllownceList = null;
        try
        {
            psclAllownceList = jdbcTemplate.query(SQL_SELECT_ALLOW_BY_SCALE_AND_GRADE,
                new Object[] {payscaleId, gradeId, payscaleId },
                new RowMapper<PrlPayscaleAllowanceInfo>()
                {

                public PrlPayscaleAllowanceInfo mapRow(ResultSet result, int rowNum) throws SQLException {
                    PrlPayscaleAllowanceInfo allwnc = new PrlPayscaleAllowanceInfo();

                    allwnc.setId(result.getLong("ID"));
                    allwnc.setBasicMinimum(result.getBigDecimal("BASIC_MINIMUM"));
                    allwnc.setBasicMaximum(result.getBigDecimal("BASIC_MAXIMUM"));
                    allwnc.setFixedBasic(result.getBoolean("FIXED_BASIC"));
                    allwnc.setActiveStatus(result.getBoolean("ACTIVE_STATUS"));

                    allwnc.setCreateBy(result.getLong("CREATE_BY"));
                    allwnc.setUpdateBy(result.getLong("UPDATE_BY"));

                    try{
                        PrlPayscaleInfo payscaleInfo = new PrlPayscaleInfo();
                        payscaleInfo.setId(result.getLong("PAYSCALE_INFO_ID"));
                        allwnc.setPayscaleInfo(payscaleInfo);
                    }
                    catch (Exception ex)
                    {
                        logger.error(""+ex.getMessage());
                    }

                    PrlAllowDeductInfo allowanceInfo = new PrlAllowDeductInfo();
                    allowanceInfo.setId(result.getLong("ALLOWANCE_INFO_ID"));
                    allowanceInfo.setName(result.getString("NAME"));
                    allowanceInfo.setDescription(result.getString("DESCRIPTION"));
                    AllowanceDeductionType type = AllowanceDeductionType.valueOf(result.getString("ALLOW_DEDUC_TYPE"));
                    allowanceInfo.setAllowDeducType(type);
                    allwnc.setAllowanceInfo(allowanceInfo);

                    //logger.info("Allowance: "+allowanceInfo.toString());
                    return allwnc;
                }

            });
        }
        catch(Exception ex)
        {
            logger.error("findAllPayscaleAllowanceByPayScaleAndGrade Msg: "+ex.getMessage());
            //throw new DAOException(ExceptionTypes.DB_QUERY_PROCESSING_FAILED, "find all failed ExpMsg: "+ex.getMessage(),ex);
        }
        return psclAllownceList;
    }

    /*
    SELECT pai.BASIC_MAXIMUM,pai.BASIC_MINIMUM, nvl(sadi.ALLOW_DEDUC_VALUE, pai.BASIC_MAXIMUM) as ALLOW_DEDUC_VALUE,
sadi.ID, nvl(sadi.ALLOW_DEDUC_TYPE, 'Allowance') ALLOW_DEDUC_TYPE, nvl(sadi.ALLOW_DEDUC_INFO_ID, pai.ALLOWANCE_INFO_ID) ALLOW_DEDUC_INFO_ID, sadi.ACTIVE_STATUS,
sadi.SALARY_STRUCTURE_INFO_ID, sadi.CREATE_BY, sadi.CREATE_DATE, sadi.UPDATE_BY, sadi.UPDATE_DATE,
(SELECT NAME FROM PRL_ALLOW_DEDUCT_INFO WHERE ID = pai.ALLOWANCE_INFO_ID) as ALLOW_DEDUC_NAME
FROM PRL_PAYSCALE_ALLOWANCE_INFO pai
LEFT OUTER JOIN PRL_SALARY_ALLOW_DEDUC_INFO sadi
 ON (pai.ALLOWANCE_INFO_ID = sadi.ALLOW_DEDUC_INFO_ID AND
 sadi.SALARY_STRUCTURE_INFO_ID =
  (SELECT ID FROM PRL_SALARY_STRUCTURE_INFO WHERE PAYSCALE_INFO_ID=10463 AND EMPLOYEE_INFO_ID=3149)
  AND sadi.ALLOW_DEDUC_TYPE like 'Allowance')
WHERE pai.PAYSCALE_INFO_ID = 10463

UNION

SELECT 0 as BASIC_MAXIMUM, 0 as BASIC_MINIMUM, nvl(sadi.ALLOW_DEDUC_VALUE, 0) as ALLOW_DEDUC_VALUE,
sadi.ID, nvl(sadi.ALLOW_DEDUC_TYPE, pai.ALLOW_DEDUC_TYPE) ALLOW_DEDUC_TYPE, nvl(sadi.ALLOW_DEDUC_INFO_ID, pai.ID) ALLOW_DEDUC_INFO_ID, sadi.ACTIVE_STATUS,
sadi.SALARY_STRUCTURE_INFO_ID, sadi.CREATE_BY, sadi.CREATE_DATE, sadi.UPDATE_BY, sadi.UPDATE_DATE,
pai.NAME as ALLOW_DEDUC_NAME

FROM
(SELECT aldd.* FROM PRL_ALLOW_DEDUCT_INFO aldd, PRL_ALLOWDEDUCT_GRADE_MAP alddmp
WHERE aldd.ID = alddmp.PRL_ALLOW_DEDUCT_INFOS_ID AND alddmp.GRADE_INFOS_ID = 5958 AND aldd.ALLOW_DEDUC_TYPE = 'Deduction') pai
LEFT OUTER JOIN
PRL_SALARY_ALLOW_DEDUC_INFO sadi
ON(pai.ID = sadi.ALLOW_DEDUC_INFO_ID AND sadi.SALARY_STRUCTURE_INFO_ID =
  (SELECT ID FROM PRL_SALARY_STRUCTURE_INFO WHERE PAYSCALE_INFO_ID=10463 AND EMPLOYEE_INFO_ID=3149)
  AND sadi.ALLOW_DEDUC_TYPE like 'Deduction')
    * */

    private final String SQL_SELECT_SALARY_STRUCTURE	= "SELECT pai.fixed_basic as FIXED_BASIC, pai.BASIC_MAXIMUM,pai.BASIC_MINIMUM, nvl(sadi.ALLOW_DEDUC_VALUE, 0) as ALLOW_DEDUC_VALUE, " +
        "sadi.ID, nvl(sadi.ALLOW_DEDUC_TYPE, 'Allowance') ALLOW_DEDUC_TYPE, nvl(sadi.ALLOW_DEDUC_INFO_ID, pai.ALLOWANCE_INFO_ID) ALLOW_DEDUC_INFO_ID, sadi.ACTIVE_STATUS, " +
        "sadi.SALARY_STRUCTURE_INFO_ID, sadi.CREATE_BY, sadi.CREATE_DATE, sadi.UPDATE_BY, sadi.UPDATE_DATE, " +
        "(SELECT NAME FROM PRL_ALLOW_DEDUCT_INFO WHERE ID = pai.ALLOWANCE_INFO_ID) as ALLOW_DEDUC_NAME " +
        "FROM PRL_PAYSCALE_ALLOWANCE_INFO pai " +
        "LEFT OUTER JOIN PRL_SALARY_ALLOW_DEDUC_INFO sadi " +
        " ON (pai.ALLOWANCE_INFO_ID = sadi.ALLOW_DEDUC_INFO_ID AND " +
        " sadi.SALARY_STRUCTURE_INFO_ID = " +
        "  (SELECT ID FROM PRL_SALARY_STRUCTURE_INFO WHERE PAYSCALE_INFO_ID=pai.PAYSCALE_INFO_ID AND EMPLOYEE_INFO_ID= ?) " +   // 1: EmployeeID
        "  AND sadi.ALLOW_DEDUC_TYPE like 'Allowance') " +
        "WHERE pai.PAYSCALE_INFO_ID = ?  " + //2: PayScaleId
        "UNION " +
        "SELECT 1 as FIXED_BASIC, 0 as BASIC_MAXIMUM, 0 as BASIC_MINIMUM, nvl(sadi.ALLOW_DEDUC_VALUE, 0) as ALLOW_DEDUC_VALUE, " +
        "sadi.ID, nvl(sadi.ALLOW_DEDUC_TYPE, pai.ALLOW_DEDUC_TYPE) ALLOW_DEDUC_TYPE, nvl(sadi.ALLOW_DEDUC_INFO_ID, pai.ID) ALLOW_DEDUC_INFO_ID, sadi.ACTIVE_STATUS, " +
        "sadi.SALARY_STRUCTURE_INFO_ID, sadi.CREATE_BY, sadi.CREATE_DATE, sadi.UPDATE_BY, sadi.UPDATE_DATE, " +
        "pai.NAME as ALLOW_DEDUC_NAME " +
        "FROM " +
        "(SELECT aldd.* FROM PRL_ALLOW_DEDUCT_INFO aldd, PRL_ALLOWDEDUCT_GRADE_MAP alddmp " +
        "WHERE aldd.ID = alddmp.PRL_ALLOW_DEDUCT_INFOS_ID AND alddmp.GRADE_INFOS_ID = ? AND aldd.ALLOW_DEDUC_TYPE = 'Deduction') pai " + // 3: GradeId
        "LEFT OUTER JOIN " +
        "PRL_SALARY_ALLOW_DEDUC_INFO sadi " +
        "ON(pai.ID = sadi.ALLOW_DEDUC_INFO_ID AND sadi.SALARY_STRUCTURE_INFO_ID = " +
        "  (SELECT ID FROM PRL_SALARY_STRUCTURE_INFO WHERE PAYSCALE_INFO_ID=? AND EMPLOYEE_INFO_ID=?) " + // 4: PayScaleId, 5: EmployeeID
        "  AND sadi.ALLOW_DEDUC_TYPE like 'Deduction')";

    public List<PrlSalaryStructureDto> findAllSalaryStructureAllowanceByFilter(Long payscaleId, Long gradeId, Long employeeId)
    {
        logger.info("find salary structure by payscale, grade and employee "+SQL_SELECT_SALARY_STRUCTURE);
        List<PrlSalaryStructureDto> psclAllownceList = null;
        try
        {
            psclAllownceList = jdbcTemplate.query(SQL_SELECT_SALARY_STRUCTURE,
                new Object[] {employeeId, payscaleId, gradeId, payscaleId, employeeId },
                new RowMapper<PrlSalaryStructureDto>()
                {
                    public PrlSalaryStructureDto mapRow(ResultSet result, int rowNum) throws SQLException {
                        PrlSalaryAllowDeducInfo allwnc = new PrlSalaryAllowDeducInfo();
                        PrlSalaryStructureDto salaryStructureDto = new PrlSalaryStructureDto();
                        try
                        {
                            allwnc.setId(result.getLong("ID"));
                            allwnc.setAllowDeducType(result.getString("ALLOW_DEDUC_TYPE"));
                            allwnc.setAllowDeducValue(result.getBigDecimal("ALLOW_DEDUC_VALUE"));
                            allwnc.setActiveStatus(result.getBoolean("ACTIVE_STATUS"));

                            allwnc.setCreateBy(result.getLong("CREATE_BY"));
                            allwnc.setUpdateBy(result.getLong("UPDATE_BY"));

                            try{
                                PrlSalaryStructureInfo salaryStructureInfo = new PrlSalaryStructureInfo();
                                salaryStructureInfo.setId(result.getLong("SALARY_STRUCTURE_INFO_ID"));
                                allwnc.setSalaryStructureInfo(salaryStructureInfo);
                            }
                            catch (Exception ex)
                            {
                                logger.error(""+ex.getMessage());
                            }

                            PrlAllowDeductInfo allowanceInfo = new PrlAllowDeductInfo();
                            allowanceInfo.setId(result.getLong("ALLOW_DEDUC_INFO_ID"));
                            allowanceInfo.setName(result.getString("ALLOW_DEDUC_NAME"));
                            //allowanceInfo.setDescription(result.getString("DESCRIPTION"));
                            AllowanceDeductionType type = AllowanceDeductionType.valueOf(result.getString("ALLOW_DEDUC_TYPE"));
                            allowanceInfo.setAllowDeducType(type);
                            allwnc.setAllowDeducInfo(allowanceInfo);

                            logger.info("AllowanceDeductInfo: "+allwnc.toString());

                            salaryStructureDto.setAllowDeducType(allwnc.getAllowDeducType());
                            salaryStructureDto.setFixedBasic(result.getBoolean("FIXED_BASIC"));
                            salaryStructureDto.setBasicMinimum(result.getBigDecimal("BASIC_MINIMUM"));
                            salaryStructureDto.setBasicMaximum(result.getBigDecimal("BASIC_MAXIMUM"));
                            salaryStructureDto.setSalaryAllowDeducInfo(allwnc);
                        }
                        catch(Exception ex)
                        {
                            logger.error("ColumnObjectConversion Error: "+ex.getMessage());
                        }
                        return salaryStructureDto;
                    }
                });
        }
        catch(Exception ex)
        {
            logger.error("findAllSalaryStructureAllowanceByFilter Msg: "+ex.getMessage());
            //throw new DAOException(ExceptionTypes.DB_QUERY_PROCESSING_FAILED, "find all failed ExpMsg: "+ex.getMessage(),ex);
        }
        return psclAllownceList;
    }

    private final String SQL_MAXMIN_HOUSERENT_BYLOCALITY = "SELECT MIN(BASIC_SALARY_MIN) as basicMin, MAX(BASIC_SALARY_MAX) as basicMax " +
        "FROM PRL_HOUSE_RENT_ALLOW_INFO WHERE GAZETTE_INFO_ID = ? AND LOCALITY_SET_INFO_ID = ? ";

    public Map<String, BigDecimal> findHouseRentMinMaxByLocalityAndGezzete(Long gazzeteId, Long localitySetId)
    {
        logger.info("find house rent min max value by locality and gezzete "+SQL_MAXMIN_HOUSERENT_BYLOCALITY);
        Map<String, BigDecimal> basicMinMax = null;
        try
        {
            basicMinMax = jdbcTemplate.queryForObject(SQL_MAXMIN_HOUSERENT_BYLOCALITY,
                new Object[]{gazzeteId, localitySetId},
                new RowMapper<Map<String, BigDecimal>>() {
                    public Map<String, BigDecimal> mapRow(ResultSet result, int rowNum) throws SQLException {
                        Map<String, BigDecimal> minMaxMap = new HashMap<>();
                        try {
                            minMaxMap.put("basicMin", result.getBigDecimal("basicMin"));
                            minMaxMap.put("basicMax", result.getBigDecimal("basicMax"));
                        } catch (Exception ex) {
                            logger.error("ColumnObjectConversion Error: " + ex.getMessage());
                        }
                        return minMaxMap;
                    }
                });
        }
        catch(Exception ex)
        {
            logger.error("findHouseRentMinMaxByLocalityAndGezzete Msg: "+ex.getMessage());
            //throw new DAOException(ExceptionTypes.DB_QUERY_PROCESSING_FAILED, "find all failed ExpMsg: "+ex.getMessage(),ex);
        }
        if(basicMinMax==null)
        {
            basicMinMax = new HashMap<>();
            basicMinMax.put("basicMin", BigDecimal.ZERO);
            basicMinMax.put("basicMax", BigDecimal.ZERO);
        }
        return basicMinMax;
    }

    private final String SQL_ECONOMICAL_CODE_LIST = "SELECT nvl(eci.CODE_TYPE, adi.ALLOW_DEDUC_TYPE) as CODE_TYPE, eci.CODE_NAME, eci.ID, eci.CREATE_DATE, eci.CREATE_BY, eci.UPDATE_DATE, eci.UPDATE_BY, " +
        "adi.NAME as CODE_TITLE, adi.ID as ALLOW_DEDUC_ID " +
        "FROM PRL_ALLOW_DEDUCT_INFO adi  " +
        "LEFT OUTER JOIN PRL_ECONOMICAL_CODE_INFO eci ON (eci.ALLOWANCE_DEDUCTION_INFO_ID = adi.ID) " +
        "ORDER BY adi.ALLOW_DEDUC_TYPE ";

    public List<PrlEconomicalCodeInfo> getEconomicalCodeListByAllAllowanceDeduction()
    {
        logger.info("find all economical code list for all allowance, deduction and salary : "+SQL_ECONOMICAL_CODE_LIST);
        List<PrlEconomicalCodeInfo> economicalCodeInfoList = null;
        try
        {
            economicalCodeInfoList = jdbcTemplate.query(SQL_ECONOMICAL_CODE_LIST,
                new RowMapper<PrlEconomicalCodeInfo>() {
                    public PrlEconomicalCodeInfo mapRow(ResultSet result, int rowNum) throws SQLException {
                        PrlEconomicalCodeInfo econCodeInfo = new PrlEconomicalCodeInfo();
                        try
                        {
                            PrlAllowDeductInfo allowanceInfo = new PrlAllowDeductInfo();
                            allowanceInfo.setId(result.getLong("ALLOW_DEDUC_ID"));
                            allowanceInfo.setName(result.getString("CODE_TITLE"));
                            AllowanceDeductionType type = AllowanceDeductionType.valueOf(result.getString("CODE_TYPE"));
                            allowanceInfo.setAllowDeducType(type);
                            econCodeInfo.setAllowanceDeductionInfo(allowanceInfo);

                            econCodeInfo.setCodeName(result.getString("CODE_NAME"));
                            econCodeInfo.setCodeType(result.getString("CODE_TYPE"));
                            econCodeInfo.setId(result.getLong("ID"));
                            logger.info("economicalCode: " + econCodeInfo.toString());

                        } catch (Exception ex) {
                            logger.error("ColumnObjectConversion Error: " + ex.getMessage());
                        }
                        return econCodeInfo;
                    }
                });
        }
        catch(Exception ex)
        {
            logger.error("getEconomicalCodeListByAllAllowanceDeduction Msg: "+ex.getMessage());
            //throw new DAOException(ExceptionTypes.DB_QUERY_PROCESSING_FAILED, "find all failed ExpMsg: "+ex.getMessage(),ex);
        }
        return economicalCodeInfoList;
    }

    private final String SQL_BUDGET_MAP_LIST = "SELECT bsi.ID, bsi.BUDGET_TYPE, bsi.BUDGET_YEAR, nvl(bsi.CODE_TYPE, code.CODE_TYPE) as CODE_TYPE, bsi.CODE_VALUE, bsi.ALLOWANCE_DEDUCTION_INFO_ID as BALDD_ID, " +
        "code.CODE_ID, code.CODE_NAME, code.ALLDD_ID, code.ALDD_NAME, bsi.CREATE_BY, bsi.CREATE_DATE, bsi.UPDATE_BY, bsi.UPDATE_DATE " +
        "FROM  " +
        "(SELECT eci.ID as CODE_ID, eci.CODE_TYPE, eci.CODE_NAME, eci.ALLOWANCE_DEDUCTION_INFO_ID as ALLDD_ID, adi.NAME as ALDD_NAME  " +
        "FROM PRL_ECONOMICAL_CODE_INFO eci, PRL_ALLOW_DEDUCT_INFO adi WHERE eci.ALLOWANCE_DEDUCTION_INFO_ID = adi.ID) code " +
        "LEFT OUTER JOIN PRL_BUDGET_SETUP_INFO bsi ON (bsi.ECONOMICAL_CODE_INFO_ID = code.CODE_ID AND bsi.BUDGET_YEAR = ? AND bsi.BUDGET_TYPE = ? ) " +
        "WHERE code.CODE_TYPE IN ('Allowance','Salary', 'HouseRent','OnetimeAllowance') ORDER BY code.CODE_TYPE ";

    public List<PrlBudgetSetupInfo> getBudgetListWithAllowanceDeductionCode(Long budgetYear, String budgetType)
    {
        logger.info("find all budget list for all allowance and salary : "+SQL_BUDGET_MAP_LIST);
        List<PrlBudgetSetupInfo> budgetSetupInfoList = null;
        try
        {
            budgetSetupInfoList = jdbcTemplate.query(SQL_BUDGET_MAP_LIST,
                new Object[]{budgetYear, budgetType},
                new RowMapper<PrlBudgetSetupInfo>() {
                    public PrlBudgetSetupInfo mapRow(ResultSet result, int rowNum) throws SQLException
                    {
                        PrlBudgetSetupInfo budgetInfo = new PrlBudgetSetupInfo();
                        try
                        {
                            PrlAllowDeductInfo allowanceInfo = new PrlAllowDeductInfo();
                            allowanceInfo.setId(result.getLong("ALLDD_ID"));
                            allowanceInfo.setName(result.getString("ALDD_NAME"));
                            AllowanceDeductionType type = AllowanceDeductionType.valueOf(result.getString("CODE_TYPE"));
                            allowanceInfo.setAllowDeducType(type);

                            PrlEconomicalCodeInfo econCodeInfo = new PrlEconomicalCodeInfo();
                            econCodeInfo.setCodeName(result.getString("CODE_NAME"));
                            econCodeInfo.setCodeType(result.getString("CODE_TYPE"));
                            econCodeInfo.setId(result.getLong("CODE_ID"));

                            budgetInfo.setBudgetType(result.getString("BUDGET_TYPE"));
                            budgetInfo.setBudgetYear(result.getLong("BUDGET_YEAR"));
                            budgetInfo.setCodeType(result.getString("CODE_TYPE"));
                            budgetInfo.setCodeValue(result.getBigDecimal("CODE_VALUE"));

                            budgetInfo.setId(result.getLong("ID"));
                            budgetInfo.setEconomicalCodeInfo(econCodeInfo);
                            budgetInfo.setAllowanceDeductionInfo(allowanceInfo);

                            logger.info("budgetInfo: " + econCodeInfo.toString());

                        } catch (Exception ex) {
                            logger.error("ColumnObjectConversion Error: " + ex.getMessage());
                        }
                        return budgetInfo;
                    }
                });
        }
        catch(Exception ex)
        {
            logger.error("getBudgetListWithAllowanceDeductionCode Msg: "+ex.getMessage());
            //throw new DAOException(ExceptionTypes.DB_QUERY_PROCESSING_FAILED, "find all failed ExpMsg: "+ex.getMessage(),ex);
        }
        return budgetSetupInfoList;
    }

    private final String SQL_MONTHLY_SALARY_GEN_PROC    = "{call PAYROLL_PROC_PACK.MON_SAL_PROC(?, ?, ?, ?)} ";
    private final String SQL_ONETIME_SALARY_GEN_PROC    = "{call PAYROLL_PROC_PACK.ONETIME_AL_PROC(?, ?, ?, ?)} ";
    private final String SQL_SALARY_DISBURSE_PROC       = "{call PAYROLL_PROC_PACK.MON_SAL_DIS(?, ?, ?, ?)} ";

    public void generateEmployeeSalaryByStoredProc(PrlGeneratedSalaryInfo genSalInfo)
        throws Exception
    {
        String PROC_NAME = genSalInfo.getSalaryType().equalsIgnoreCase(PayrollManagementConstant.SALARY_GEN_ONETIME) ? SQL_ONETIME_SALARY_GEN_PROC : SQL_MONTHLY_SALARY_GEN_PROC;
        logger.info("SalaryGeneration: " + PROC_NAME);
        Connection conn =   jdbcTemplate.getDataSource().getConnection();
        CallableStatement callStat = null;
        callStat = conn.prepareCall(PROC_NAME);
        callStat.setString(1,genSalInfo.getMonthName());
        callStat.setLong(2, genSalInfo.getYearName());
        callStat.setLong(3, genSalInfo.getCreateBy());
        //callStat.setDate(4, (java.sql.Date) new Date());
        callStat.setString(4, genSalInfo.getSalaryType());
        callStat.execute();
    }

    public void generateEmployeeSalaryByStoredProc(PrlSalaryGenerationDto salGenDto)
        throws Exception
    {
        String PROC_NAME = salGenDto.getSalaryType().equalsIgnoreCase(PayrollManagementConstant.SALARY_GEN_ONETIME) ? SQL_ONETIME_SALARY_GEN_PROC : SQL_MONTHLY_SALARY_GEN_PROC;
        logger.info("SalaryGenerationDTO: " + PROC_NAME);
        Connection conn =   jdbcTemplate.getDataSource().getConnection();
        CallableStatement callStat = null;
        callStat = conn.prepareCall(PROC_NAME);
        callStat.setString(1,salGenDto.getMonthName());
        callStat.setLong(2, salGenDto.getYear());
        callStat.setLong(3, salGenDto.getCreateBy());
        //callStat.setDate(4, (java.sql.Date) new Date());
        callStat.setString(4, salGenDto.getSalaryType());
        callStat.execute();
    }

    public void updateDisburseStatusByStoredProc(PrlGeneratedSalaryInfo genSalInfo)
        throws Exception
    {
        logger.info("SalaryDisburseDTO: " + SQL_SALARY_DISBURSE_PROC);
        Connection conn =   jdbcTemplate.getDataSource().getConnection();
        CallableStatement callStat = null;
        callStat = conn.prepareCall(SQL_SALARY_DISBURSE_PROC);
        callStat.setString(1,genSalInfo.getMonthName());
        callStat.setLong(2, genSalInfo.getYearName());
        callStat.setLong(3, genSalInfo.getCreateBy());
        //callStat.setDate(4, (java.sql.Date) new Date());
        callStat.setString(4, genSalInfo.getSalaryType());
        callStat.execute();
    }
}
