package gov.step.app.web.rest.jdbc.dao;

/**
 * Created by rana on 12/21/15.
 */

import gov.step.app.security.SecurityUtils;
import gov.step.app.web.rest.dto.CatJobDto;
import gov.step.app.web.rest.dto.RisJobPostingDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLDataException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class RptJdbcDao {
    private final Logger log = LoggerFactory.getLogger(RptJdbcDao.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RptJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> generateSalary() {
        return jdbcTemplate.queryForList("select pay_scale.basic_amount,pay_scale.code,pay_scale.house_allowance,pay_scale.medical_allowance " +
            ",(pay_scale.retirement_amount * pay_scale.basic_amount/100) retirementAmount " +
            ",(pay_scale.welfare_amount * pay_scale.basic_amount/100) welfareAmount " +
            ", (pay_scale.basic_amount+pay_scale.house_allowance+pay_scale.medical_allowance-(pay_scale.retirement_amount * pay_scale.basic_amount/100)-(welfare_amount * basic_amount/100) ) total " +
            "from mpo_application mpo " +
            "join inst_employee employee on (employee.id=mpo.inst_employee_id)   " +
            "join pay_scale pay_scale on (pay_scale.id=employee.pay_scale_id) " +
            "where employee.pay_scale_id is not null");
    }

    public boolean isSalaryGenerateForMonthAndYear(int year, String month) {
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from mpo_salary_mst " +
            "where FOR_THE_MONTH like ? and FOR_THE_YEAR= ? ", month, year);

        String years = String.valueOf(year);
        jdbcTemplate.update("call MPO_SAL_PROC (?, ?, ?, ?)", month, years, "1", "1");
        return false;

        /*if (list != null && list.size() > 0) {
            return true;
        } else {
            //MPO_SAL_PROC ('January','2014','1','1');
            String years = String.valueOf(year);
            jdbcTemplate.update("call MPO_SAL_PROC (?, ?, ?, ?)", month, years, "1", "1");
            return false;
        }*/
    }
    public boolean disburseByMonthAndYear(int year, String month) {
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from mpo_salary_mst " +
            "where FOR_THE_MONTH like ? and FOR_THE_YEAR= ? ", month, year);

        String years = String.valueOf(year);
        jdbcTemplate.update("call SALARY_DISBURSE (?, ?)", month, years);
        return false;

        /*if (list != null && list.size() > 0) {
            return true;
        } else {
            //MPO_SAL_PROC ('January','2014','1','1');
            String years = String.valueOf(year);
            jdbcTemplate.update("call MPO_SAL_PROC (?, ?, ?, ?)", month, years, "1", "1");
            return false;
        }*/
    }
/*
    public List<Map<String,Object>>CatByJobs(){
        List<Map<String,Object>> list=null;
        list=jdbcTemplate.queryForList("select * from JOB where CAT_ID=1003 ORDER BY ID desc");
        if(list !=null && list.size()>0){
            return list;
        }else{
            return new ArrayList<>();
        }
    }*/


    public List<Map<String, Object>> findListByInstEmployeeId(Long id) {
        List<Map<String, Object>> list = null;
        if (id > 0) {
            list = jdbcTemplate.queryForList("select instEmpEduQuali.* " +
                "from inst_emp_edu_quali instEmpEduQuali " +
                "where instEmpEduQuali.INST_EMPLOYEE_ID =? ", id);
        }

        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    public List<Map<String, Object>> findPendingListByInstituteId(Long instituteId, int status) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select mpo.id mpo_id, mpo_application_date app_date, emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,mpo.status app_status  " +
            "from mpo_application mpo " +
            "join inst_employee emp on (emp.id=mpo.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where mpo.status= ? and institute.id=?  order by mpo.mpo_application_date desc", status, instituteId);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    public List<Map<String, Object>> findApprovedListByInstituteId(Long instituteId, int status) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select mpo.id mpo_id, mpo_application_date app_date, emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,mpo.status app_status  " +
            "from mpo_application mpo " +
            "join inst_employee emp on (emp.id=mpo.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where mpo.status >= ? and institute.id=? order by mpo.mpo_application_date desc ", status, instituteId);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }


    public List<Map<String, Object>> findApprovedListByDistrictId(Long districtId, int status) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select mpo.id mpo_id, mpo_application_date app_date, emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,mpo.status app_status  " +
            "from mpo_application mpo " +
            "join inst_employee emp on (emp.id=mpo.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where mpo.status >= ? and upazila.district_id = ?  order by mpo.mpo_application_date desc", status, districtId);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    public List<Map<String, Object>> findPendingListByDistrictId(Long districtId, int status) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select mpo.id mpo_id, mpo_application_date app_date, emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,mpo.status app_status  " +
            "from mpo_application mpo " +
            "join inst_employee emp on (emp.id=mpo.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where mpo.status = ? and upazila.district_id= ? order by mpo.mpo_application_date desc  ", status, districtId);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    public List<Map<String, Object>> findPendingListByDistrictIdAndTeacherLevel(Long districtId, int status, Long levelId) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select mpo.id mpo_id, mpo_application_date app_date, emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,mpo.status app_status  " +
            "from mpo_application mpo " +
            "join inst_employee emp on (emp.id=mpo.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join inst_level on (inst_level.id=emp.inst_level_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where mpo.status = ? and upazila.district_id= ?   " +
            "and inst_level.id = ? order by mpo.mpo_application_date desc  ", status, districtId, levelId);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    //TODO: change mpo to related table
    public List<Map<String, Object>> findTimescalePendingListByDistrictIdAndTeacherLevel(Long districtId, int status, Long levelId) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select mpo.id mpo_id, emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,mpo.status app_status  " +
            "from time_scale_application mpo " +
            "join inst_employee emp on (emp.id=mpo.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join inst_level on (inst_level.id=emp.inst_level_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where mpo.status = ? and upazila.district_id= ?   " +
            "and inst_level.id = ?  order by mpo.date_created desc", status, districtId, levelId);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    public List<Map<String, Object>> findBEdPendingListByDistrictIdAndTeacherLevel(Long districtId, int status, Long levelId) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select mpo.id mpo_id, emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,mpo.status app_status  " +
            "from b_ed_application mpo " +
            "join inst_employee emp on (emp.id=mpo.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join inst_level on (inst_level.id=emp.inst_level_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where mpo.status = ? and upazila.district_id= ?   " +
            "and inst_level.id = ?  order by mpo.created_date desc", status, districtId, levelId);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    public List<Map<String, Object>> findAPPendingListByDistrictIdAndTeacherLevel(Long districtId, int status, Long levelId) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select mpo.id mpo_id, mpo_application_date app_date, emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,mpo.status app_status  " +
            "from ap_scale_application mpo " +
            "join inst_employee emp on (emp.id=mpo.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join inst_level on (inst_level.id=emp.inst_level_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where mpo.status = ? and upazila.district_id= ?   " +
            "and inst_level.id = ?  order by mpo.date_created desc", status, districtId, levelId);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }


    public List<Map<String, Object>> findPrinciplePendingListByDistrictIdAndTeacherLevel(Long districtId, int status, Long levelId) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select mpo.id mpo_id, emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,mpo.status app_status  " +
            "from professor_application mpo " +
            "join inst_employee emp on (emp.id=mpo.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join inst_level on (inst_level.id=emp.inst_level_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where mpo.status = ? and upazila.district_id= ?   " +
            "and inst_level.id = ?  order by mpo.date_created desc", status, districtId, levelId);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }


    public List<Map<String, Object>> findMpoPendingListByStatus(int status) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select mpo.id mpo_id, mpo_application_date app_date, emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,mpo.status app_status  " +
            "from mpo_application mpo " +
            "join inst_employee emp on (emp.id=mpo.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where mpo.status = ?   order by mpo.mpo_application_date desc", status);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    public List<Map<String, Object>> findMpoPendingListByStatusAndLevel(int status, long levelId) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select mpo.id mpo_id, mpo_application_date app_date, emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,mpo.status app_status  " +
            "from mpo_application mpo " +
            "join inst_employee emp on (emp.id=mpo.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join inst_level on (inst_level.id=emp.inst_level_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where mpo.status = ?  and inst_level.id = ?  order by mpo.mpo_application_date desc ", status, levelId);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }


    public List<Map<String, Object>> findTimescalePendingListByStatusAndLevel(int status, long levelId) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select mpo.id mpo_id, date_created app_date, emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,mpo.status app_status  " +
            "from time_scale_application mpo " +
            "join inst_employee emp on (emp.id=mpo.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join inst_level on (inst_level.id=emp.inst_level_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where mpo.status = ?  and inst_level.id = ? order by mpo.date_created desc ", status, levelId);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }


    public List<Map<String, Object>> findBEdPendingListByStatusAndLevel(int status, long levelId) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select mpo.id mpo_id, date_created app_date, emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,mpo.status app_status  " +
            "from b_ed_application mpo " +
            "join inst_employee emp on (emp.id=mpo.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join inst_level on (inst_level.id=emp.inst_level_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where mpo.status = ?  and inst_level.id = ? order by mpo.created_date desc ", status, levelId);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }


    public List<Map<String, Object>> findProfessorPendingListByStatusAndLevel(int status, long levelId) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select mpo.id mpo_id, date_created app_date, emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,mpo.status app_status  " +
            "from professor_application mpo " +
            "join inst_employee emp on (emp.id=mpo.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join inst_level on (inst_level.id=emp.inst_level_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where mpo.status = ?  and inst_level.id = ? order by mpo.date_created desc ", status, levelId);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }


    public List<Map<String, Object>> findAPPendingListByStatusAndLevel(int status, long levelId) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select mpo.id mpo_id, date_created app_date, emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,mpo.status app_status  " +
            "from ap_scale_application mpo " +
            "join inst_employee emp on (emp.id=mpo.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join inst_level on (inst_level.id=emp.inst_level_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where mpo.status = ?  and inst_level.id = ? order by mpo.date_created desc ", status, levelId);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }


    public List<Map<String, Object>> findMpoSummaryShitByStatus(int status) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select emp.id employee_id, mpo.id mpo_id, " +
            "mpo.date_cmt_approved cmt_approve_date, mpo.date_deo_approved deo_approve_date, " +
            "mpo.dg_representative dg_rePresentative, " +
            "recruit.present_inst_join_date join_date, recruit.gb_resol_receive_date gb_rec_date, " +
            "mpo.gov_order gov_order, mpo.mpo_application_date application_date, " +
            "emp.dob dob, emp.index_no index_no, emp.name name, emp.registered_Certificate_no registered_no, " +
            "inst.name inst_name, inst.code inst_code, " +
            "upz.name upz_name, dst.name district, bank.name bank_name, " +
            "emp_bank.bank_account_no bank_account, desg.name designation, " +
            "sub.name subject, recruit.dg_represent_name dg_representitive, pay_scale.code pay_code  " +
            "from mpo_application mpo " +
            "left join inst_employee emp on mpo.inst_employee_id = emp.id " +
            "left join institute inst on emp.institute_id = inst.id " +
            "left join inst_empl_bank_info emp_bank on emp.id = emp_bank.inst_employee_id " +
            "left join bank_setup bank on bank.id = emp_bank.id " +
            "left join upazila upz on upz.id = inst.upazila_id " +
            "left join district dst on dst.id = upz.district_id " +
            "left join inst_empl_designation desg on emp.inst_empl_designation_id = desg.id " +
            "left join cms_sub_assign sub_assgin on sub_assgin.id = emp.cms_sub_assign_id " +
            "left join cms_subject sub on sub_assgin.cms_subject_id = sub.id " +
            "left join inst_empl_recruit_info recruit on recruit.inst_employee_id = emp.id " +
            "left join pay_scale pay_scale on pay_scale.id = emp.pay_scale_id " +
            "where mpo.status  = ?  order by mpo.mpo_application_date desc ", status);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    public List<Map<String, Object>> findTimescaleSummaryShitByStatus(int status) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select emp.id employee_id, mpo.id mpo_id, " +
            "mpo.date_cmt_approved cmt_approve_date, mpo.date_deo_approved deo_approve_date, " +
            "mpo.dg_representative dg_rePresentative, " +
            "recruit.present_inst_join_date join_date, recruit.gb_resol_receive_date gb_rec_date, " +
            "mpo.gov_order gov_order, mpo.date_created application_date, " +
            "emp.dob dob, emp.index_no index_no, emp.name name, emp.registered_Certificate_no registered_no, " +
            "inst.name inst_name, inst.code inst_code, " +
            "upz.name upz_name, dst.name district, bank.name bank_name, " +
            "emp_bank.bank_account_no bank_account, desg.name designation, " +
            "sub.name subject, recruit.dg_represent_name dg_representitive, pay_scale.code pay_code  " +
            "from time_scale_application mpo " +
            "left join inst_employee emp on mpo.inst_employee_id = emp.id " +
            "left join institute inst on emp.institute_id = inst.id " +
            "left join inst_empl_bank_info emp_bank on emp.id = emp_bank.inst_employee_id " +
            "left join bank_setup bank on bank.id = emp_bank.id " +
            "left join upazila upz on upz.id = inst.upazila_id " +
            "left join district dst on dst.id = upz.district_id " +
            "left join inst_empl_designation desg on emp.inst_empl_designation_id = desg.id " +
            "left join cms_sub_assign sub_assgin on sub_assgin.id = emp.cms_sub_assign_id " +
            "left join cms_subject sub on sub_assgin.cms_subject_id = sub.id " +
            "left join inst_empl_recruit_info recruit on recruit.inst_employee_id = emp.id " +
            "left join pay_scale pay_scale on pay_scale.id = emp.pay_scale_id " +
            "where mpo.status  = ?  order by mpo.date_created desc ", status);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    public List<Map<String, Object>> findBEdSummaryShitByStatus(int status) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select emp.id employee_id, mpo.id mpo_id, " +
            "mpo.date_cmt_approved cmt_approve_date, mpo.date_deo_approved deo_approve_date, " +
            "mpo.dg_representative dg_rePresentative, " +
            "recruit.present_inst_join_date join_date, recruit.gb_resol_receive_date gb_rec_date, " +
            "mpo.gov_order gov_order, mpo.date_created application_date, " +
            "emp.dob dob, emp.index_no index_no, emp.name name, emp.registered_Certificate_no registered_no, " +
            "inst.name inst_name, inst.code inst_code, " +
            "upz.name upz_name, dst.name district, bank.name bank_name, " +
            "emp_bank.bank_account_no bank_account, desg.name designation, " +
            "sub.name subject, recruit.dg_represent_name dg_representitive, pay_scale.code pay_code  " +
            "from b_ed_application mpo " +
            "left join inst_employee emp on mpo.inst_employee_id = emp.id " +
            "left join institute inst on emp.institute_id = inst.id " +
            "left join inst_empl_bank_info emp_bank on emp.id = emp_bank.inst_employee_id " +
            "left join bank_setup bank on bank.id = emp_bank.id " +
            "left join upazila upz on upz.id = inst.upazila_id " +
            "left join district dst on dst.id = upz.district_id " +
            "left join inst_empl_designation desg on emp.inst_empl_designation_id = desg.id " +
            "left join cms_sub_assign sub_assgin on sub_assgin.id = emp.cms_sub_assign_id " +
            "left join cms_subject sub on sub_assgin.cms_subject_id = sub.id " +
            "left join inst_empl_recruit_info recruit on recruit.inst_employee_id = emp.id " +
            "left join pay_scale pay_scale on pay_scale.id = emp.pay_scale_id " +
            "where mpo.status  = ?  order by mpo.created_date desc ", status);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    public List<Map<String, Object>> findAPSummaryShitByStatus(int status) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select emp.id employee_id, mpo.id mpo_id, " +
            "mpo.date_cmt_approved cmt_approve_date, mpo.date_deo_approved deo_approve_date, " +
            "mpo.dg_representative dg_rePresentative, " +
            "recruit.present_inst_join_date join_date, recruit.gb_resol_receive_date gb_rec_date, " +
            "mpo.gov_order gov_order, mpo.date_created application_date, " +
            "emp.dob dob, emp.index_no index_no, emp.name name, emp.registered_Certificate_no registered_no, " +
            "inst.name inst_name, inst.code inst_code, " +
            "upz.name upz_name, dst.name district, bank.name bank_name, " +
            "emp_bank.bank_account_no bank_account, desg.name designation, " +
            "sub.name subject, recruit.dg_represent_name dg_representitive, pay_scale.code pay_code  " +
            "from ap_scale_application mpo " +
            "left join inst_employee emp on mpo.inst_employee_id = emp.id " +
            "left join institute inst on emp.institute_id = inst.id " +
            "left join inst_empl_bank_info emp_bank on emp.id = emp_bank.inst_employee_id " +
            "left join bank_setup bank on bank.id = emp_bank.id " +
            "left join upazila upz on upz.id = inst.upazila_id " +
            "left join district dst on dst.id = upz.district_id " +
            "left join inst_empl_designation desg on emp.inst_empl_designation_id = desg.id " +
            "left join cms_sub_assign sub_assgin on sub_assgin.id = emp.cms_sub_assign_id " +
            "left join cms_subject sub on sub_assgin.cms_subject_id = sub.id " +
            "left join inst_empl_recruit_info recruit on recruit.inst_employee_id = emp.id " +
            "left join pay_scale pay_scale on pay_scale.id = emp.pay_scale_id " +
            "where mpo.status  = ?  order by mpo.date_created desc ", status);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    public List<Map<String, Object>> findPrincipleSummaryShitByStatus(int status) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select emp.id employee_id, mpo.id mpo_id, " +
            "mpo.date_cmt_approved cmt_approve_date, mpo.date_deo_approved deo_approve_date, " +
            "mpo.dg_representative dg_rePresentative, " +
            "recruit.present_inst_join_date join_date, recruit.gb_resol_receive_date gb_rec_date, " +
            "mpo.gov_order gov_order, mpo.date_created application_date, " +
            "emp.dob dob, emp.index_no index_no, emp.name name, emp.registered_Certificate_no registered_no, " +
            "inst.name inst_name, inst.code inst_code, " +
            "upz.name upz_name, dst.name district, bank.name bank_name, " +
            "emp_bank.bank_account_no bank_account, desg.name designation, " +
            "sub.name subject, recruit.dg_represent_name dg_representitive, pay_scale.code pay_code  " +
            "from professor_application mpo " +
            "left join inst_employee emp on mpo.inst_employee_id = emp.id " +
            "left join institute inst on emp.institute_id = inst.id " +
            "left join inst_empl_bank_info emp_bank on emp.id = emp_bank.inst_employee_id " +
            "left join bank_setup bank on bank.id = emp_bank.id " +
            "left join upazila upz on upz.id = inst.upazila_id " +
            "left join district dst on dst.id = upz.district_id " +
            "left join inst_empl_designation desg on emp.inst_empl_designation_id = desg.id " +
            "left join cms_sub_assign sub_assgin on sub_assgin.id = emp.cms_sub_assign_id " +
            "left join cms_subject sub on sub_assgin.cms_subject_id = sub.id " +
            "left join inst_empl_recruit_info recruit on recruit.inst_employee_id = emp.id " +
            "left join pay_scale pay_scale on pay_scale.id = emp.pay_scale_id " +
            "where mpo.status  = ?  order by mpo.date_created desc ", status);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    public List<Map<String, Object>> findMpoListByApproveStatus(int status) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select mpo.id mpo_id, mpo_application_date app_date, emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,mpo.status app_status  " +
            "from mpo_application mpo " +
            "join inst_employee emp on (emp.id=mpo.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where mpo.status >= ? order by mpo.mpo_application_date desc ", status);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    public List<Map<String, Object>> findMpoFinalPendingList(int status, int maxStatus) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select mpo.id mpo_id, mpo_application_date app_date, emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,mpo.status app_status  " +
            "from mpo_application mpo " +
            "join inst_employee emp on (emp.id=mpo.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where mpo.status >= ?  and mpo.status < ? order by mpo.mpo_application_date desc ", status, maxStatus);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    public List<Map<String, Object>> findMpoPendingListForAdmin(int status) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select mpo.id mpo_id, mpo_application_date app_date, emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,mpo.status app_status  " +
            "from mpo_application mpo " +
            "join inst_employee emp on (emp.id=mpo.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where mpo.status < ? order by mpo.mpo_application_date desc ", status);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    public List<Map<String, Object>> findMpoPendingListForMpoCommittee(int status, String ids) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select mpo.id mpo_id, mpo_application_date app_date, emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,mpo.status app_status  " +
            "from mpo_application mpo " +
            "join inst_employee emp on (emp.id=mpo.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where mpo.status >= ? and mpo.id not in (" + ids + ")", status);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    public List<Map<String, Object>> findTimeScalePendingListForAdmin(int status) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select timescale.id timescale_id,emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,timescale.status app_status  " +
            "from time_scale_application timescale " +
            "join inst_employee emp on (emp.id=timescale.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where timescale.status < ? order by timescale.date_created desc ", status);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    public List<Map<String, Object>> findAllGeninfoBycurrentUser(Long inst) {
        List<Map<String, Object>> list = null;
        list = jdbcTemplate.queryForList("SELECT * from inst_gen_info inst_g,institute  WHERE institute.id = inst_g.institute_id AND institute.id =? ", inst);
        if (list != null && list.size() > 0) {
            System.out.println(list);
            return list;
        } else {
            return new ArrayList<>();
        }
    }

    public List<Map<String, Object>> findAPScalePendingListForAdmin(int status) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select timescale.id timescale_id,emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,timescale.status app_status  " +
            "from ap_scale_application timescale " +
            "join inst_employee emp on (emp.id=timescale.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where timescale.status < ? order by timescale.date_created desc ", status);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    public List<Map<String, Object>> findTimeScaleApproveListByStatus(int status) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select timescale.id timescale_id,emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,timescale.status app_status  " +
            "from time_scale_application timescale " +
            "join inst_employee emp on (emp.id=timescale.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where timescale.status >= ? order by timescale.date_created desc ", status);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    public List<Map<String, Object>> findNameCnclApproveListByStatus(int status) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select nameCncl.id nameCncl_id,emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,nameCncl.status app_status  " +
            "from name_cncl_application nameCncl " +
            "join inst_employee emp on (emp.id=nameCncl.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where nameCncl.status >= ?", status);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }


    public List<Map<String, Object>> findAPScaleApproveListByStatus(int status) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select timescale.id timescale_id,emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,timescale.status app_status  " +
            "from ap_scale_application timescale " +
            "join inst_employee emp on (emp.id=timescale.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where timescale.status >= ? order by timescale.date_created desc ", status);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    public List<Map<String, Object>> findTimeScalePendingListByStatus(int status) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select timescale.id timescale_id,emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,timescale.status app_status  " +
            "from time_scale_application timescale " +
            "join inst_employee emp on (emp.id=timescale.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where timescale.status = ? order by timescale.date_created desc  ", status);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }


    public List<Map<String, Object>> findTimeScaleApPendingListByStatus(int status) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select institute.name inst_name, institute.id inst_id" +
            "from time_scale_application timescale " +
            "join inst_employee emp on (emp.id=timescale.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where timescale.status = ? " +
            "group by institute.name, timescale.date_created, institute.id " +
            "having count(institute.id) > 6 " +
            "order by timescale.date_created desc  ", status);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    public List<Map<String, Object>> findNameCnclPendingListByStatus(int status) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select nameCncl.id nameCncl_id,emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,nameCncl.status app_status  " +
            "from name_cncl_application nameCncl " +
            "join inst_employee emp on (emp.id=nameCncl.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where nameCncl.status = ? ", status);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }


    public List<Map<String, Object>> findAPScalePendingListByStatus(int status) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select timescale.id timescale_id,emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,timescale.status app_status  " +
            "from ap_scale_application timescale " +
            "join inst_employee emp on (emp.id=timescale.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where timescale.status = ?  order by timescale.date_created desc ", status);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    /*
    Get Institute to send email and notification
     */

    public List<Map<String, Object>> findInstituteIdsByMonthAndYearMPOSheet(String reportMonth, int reportYear) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select distinct mpo_salary_dtl.institute_id " +
            "from mpo_salary_mst left join mpo_salary_dtl on mpo_salary_mst.id = mpo_salary_dtl.salary_mst_id " +
            "where mpo_salary_mst.for_the_month= ? and mpo_salary_mst.for_the_year= ? ", reportMonth, reportYear);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    /*
    Get Teacher to send email and notification
     */

    public List<Map<String, Object>> findTeacherIdsByMonthAndYearMPOSheet(String reportMonth, int reportYear) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select mpo_salary_dtl.inst_emp_id " +
            "from mpo_salary_mst left join mpo_salary_dtl on mpo_salary_mst.id = mpo_salary_dtl.salary_mst_id " +
            "where mpo_salary_mst.for_the_month= ? and mpo_salary_mst.for_the_year= ? ", reportMonth, reportYear);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    public List<Map<String, Object>> findTimeScalePendingListByDistrictId(Long districtId, int status) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select timescale.id timescale_id,emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,timescale.status app_status  " +
            "from time_scale_application timescale " +
            "join inst_employee emp on (emp.id=timescale.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where timescale.status = ? and upazila.district_id= ? order by timescale.date_created desc ", status, districtId);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    public List<Map<String, Object>> findNameCnclPendingListByDistrictId(Long districtId, int status) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select nameCncl.id nameCncl_id,emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,nameCncl.status app_status  " +
            "from name_cncl_application nameCncl " +
            "join inst_employee emp on (emp.id=nameCncl.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where nameCncl.status = ? and upazila.district_id= ? ", status, districtId);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    public List<Map<String, Object>> findTimeScaleListByPayPayScaleAssignStatus(int payscaleAssignStatus) {
        System.out.println("------------------------------------------------------" + payscaleAssignStatus);
        log.debug("------------------------------------------------------" + payscaleAssignStatus);
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select timescale.id id,emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,timescale.status app_status  " +
            "from time_scale_application timescale " +
            "join inst_employee emp on (emp.id=timescale.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where emp.timescale_app_status = ? ", payscaleAssignStatus);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    public List<Map<String, Object>> findNameCnclListByPayScaleAssignStatus(int payscaleAssignStatus) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select nameCncl.id nameCncl_id,emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,nameCncl.status app_status  " +
            "from name_cncl_application nameCncl " +
            "join inst_employee emp on (emp.id=nameCncl.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where emp.name_cncl_app_status = ? ", payscaleAssignStatus);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    public List<Map<String, Object>> findAPScalePendingListByDistrictId(Long districtId, int status) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select timescale.id timescale_id,emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,timescale.status app_status  " +
            "from ap_scale_application timescale " +
            "join inst_employee emp on (emp.id=timescale.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where timescale.status = ? and upazila.district_id= ? order by timescale.date_created desc ", status, districtId);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    public List<Map<String, Object>> findTimeScalePendingListByInstituteId(Long instituteId, int status) {
        List<Map<String, Object>> list = null;
System.out.println("Suuuuuuuuuuuuuuuuuvvvvvvvvvvvvvvvvvooooooooooooooooooo");
System.out.println("Institute id :"+ instituteId+ "status :"+status);
        list = jdbcTemplate.queryForList("select timescale.id timescale_id,emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,timescale.status app_status  " +
            "from time_scale_application timescale " +
            "join inst_employee emp on (emp.id=timescale.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where timescale.status= ? and institute.id=? order by timescale.date_created desc ", status, instituteId);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    public List<Map<String, Object>> findNameCnclPendingListByInstituteId(Long instituteId, int status) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select nameCncl.id timescale_id,emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,nameCncl.status app_status  " +
            "from name_cncl_application nameCncl " +
            "join inst_employee emp on (emp.id=nameCncl.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where nameCncl.status= ? and institute.id=? ", status, instituteId);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    public List<Map<String, Object>> findAPScalePendingListByInstituteId(Long instituteId, int status) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select timescale.id timescale_id,emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,timescale.status app_status  " +
            "from ap_scale_application timescale " +
            "join inst_employee emp on (emp.id=timescale.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where timescale.status= ? and institute.id=? order by timescale.date_created desc ", status, instituteId);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }


    public List<Map<String, Object>> findTimeScaleApprovedListByInstituteId(Long instituteId, int status) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select timescale.id timescale_id,emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,timescale.status app_status  " +
            "from time_scale_application timescale " +
            "join inst_employee emp on (emp.id=timescale.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where timescale.status >= ? and institute.id=? order by timescale.date_created desc ", status, instituteId);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    public List<Map<String, Object>> findNameCnclApprovedListByInstituteId(Long instituteId, int status) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select nameCncl.id nameCncl_id,emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,nameCncl.status app_status  " +
            "from name_cncl_application nameCncl " +
            "join inst_employee emp on (emp.id=nameCncl.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where nameCncl.status >= ? and institute.id=? ", status, instituteId);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    public List<Map<String, Object>> findAPScaleApprovedListByInstituteId(Long instituteId, int status) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select timescale.id timescale_id,emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,timescale.status app_status  " +
            "from ap_scale_application timescale " +
            "join inst_employee emp on (emp.id=timescale.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where timescale.status >= ? and institute.id=? order by timescale.date_created desc ", status, instituteId);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    public List<Map<String, Object>> findTimeScaleApprovedListByDistrictId(Long districtId, int status) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select timescale.id timescale_id,emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,timescale.status app_status  " +
            "from time_scale_application timescale " +
            "join inst_employee emp on (emp.id=timescale.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where timescale.status >= ? and upazila.district_id = ? order by timescale.date_created desc ", status, districtId);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    public List<Map<String, Object>> findNameCnclApprovedListByDistrictId(Long districtId, int status) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select nameCncl.id nameCncl_id,emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,nameCncl.status app_status  " +
            "from name_cncl_application nameCncl " +
            "join inst_employee emp on (emp.id=nameCncl.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where nameCncl.status >= ? and upazila.district_id = ? ", status, districtId);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    public List<Map<String, Object>> findAPScaleApprovedListByDistrictId(Long districtId, int status) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select timescale.id timescale_id,emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,timescale.status app_status  " +
            "from ap_scale_application timescale " +
            "join inst_employee emp on (emp.id=timescale.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where timescale.status >= ? and upazila.district_id = ? order by timescale.date_created desc ", status, districtId);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    public List<Map<String, Object>> findAPScaleListByPayPayScaleAssignStatus(int payscaleAssignStatus) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select timescale.id id,emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,timescale.status app_status  " +
            "from ap_scale_application timescale " +
            "join inst_employee emp on (emp.id=timescale.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where emp.ap_app_status = ?  order by timescale.date_created desc", payscaleAssignStatus);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }


/*Principle Scale Application Start */


    public List<Map<String, Object>> findPrincipleScalePendingListForAdmin(int status) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select timescale.id timescale_id,emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,timescale.status app_status  " +
            "from professor_application timescale " +
            "join inst_employee emp on (emp.id=timescale.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where timescale.status < ? order by timescale.date_created desc ", status);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }


    public List<Map<String, Object>> findPrincipleScaleApproveListByStatus(int status) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select timescale.id timescale_id,emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,timescale.status app_status  " +
            "from professor_application timescale " +
            "join inst_employee emp on (emp.id=timescale.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where timescale.status >= ? order by timescale.date_created desc ", status);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }


    public List<Map<String, Object>> findPrincipleScalePendingListByStatus(int status) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select timescale.id timescale_id,emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,timescale.status app_status  " +
            "from professor_application timescale " +
            "join inst_employee emp on (emp.id=timescale.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where timescale.status = ?  order by timescale.date_created desc ", status);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }


    public List<Map<String, Object>> findPrincipleScalePendingListByDistrictId(Long districtId, int status) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select timescale.id timescale_id,emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,timescale.status app_status  " +
            "from professor_application timescale " +
            "join inst_employee emp on (emp.id=timescale.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where timescale.status = ? and upazila.district_id= ?  order by timescale.date_created desc ", status, districtId);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }


    public List<Map<String, Object>> findPrincipleScalePendingListByInstituteId(Long instituteId, int status) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select timescale.id timescale_id,emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,timescale.status app_status  " +
            "from professor_application timescale " +
            "join inst_employee emp on (emp.id=timescale.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where timescale.status= ? and institute.id=? order by timescale.date_created desc ", status, instituteId);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }


    public List<Map<String, Object>> findPrincipleScaleApprovedListByInstituteId(Long instituteId, int status) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select timescale.id timescale_id,emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,timescale.status app_status  " +
            "from professor_application timescale " +
            "join inst_employee emp on (emp.id=timescale.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where timescale.status >= ? and institute.id=? order by timescale.date_created desc ", status, instituteId);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }


    public List<Map<String, Object>> findPrincipleScaleApprovedListByDistrictId(Long districtId, int status) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select timescale.id timescale_id,emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,timescale.status app_status  " +
            "from professor_application timescale " +
            "join inst_employee emp on (emp.id=timescale.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where timescale.status >= ? and upazila.district_id = ? order by timescale.date_created desc ", status, districtId);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    public List<Map<String, Object>> findPrincipleScaleListByPayPayScaleAssignStatus(int payscaleAssignStatus) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select timescale.id id,emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,timescale.status app_status  " +
            "from professor_application timescale " +
            "join inst_employee emp on (emp.id=timescale.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where emp.principle_app_status = ? order by timescale.date_created desc ", payscaleAssignStatus);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }



/*Principle Scale Application End*/

    public List<Map<String, Object>> findBEdListByApproveStatus(int status) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select bed.id bed_id,emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,bed.status app_status  " +
            "from b_ed_application bed " +
            "join inst_employee emp on (emp.id=bed.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where bed.status >= ? order by bed.created_date desc ", status);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    public List<Map<String, Object>> findBEdPendingListByStatus(int status) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select bed.id bed_id,emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,bed.status app_status  " +
            "from b_ed_application bed " +
            "join inst_employee emp on (emp.id=bed.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where bed.status = ? order by bed.created_date desc  ", status);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    public List<Map<String, Object>> findBEdPendingListByDistrictId(Long districtId, int status) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select bed.id bed_id,emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,bed.status app_status  " +
            "from b_ed_application bed " +
            "join inst_employee emp on (emp.id=bed.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where bed.status = ? and upazila.district_id= ? order by bed.created_date desc ", status, districtId);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    public List<Map<String, Object>> findBEdListByPayPayScaleAssignStatus(int payscaleAssignStatus) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select bed.id id,emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,bed.status app_status  " +
            "from b_ed_application bed " +
            "join inst_employee emp on (emp.id=bed.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where emp.bed_app_status = ? order by bed.created_date desc ", payscaleAssignStatus);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    public List<Map<String, Object>> findBEdPendingListByInstituteId(Long instituteId, int status) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select bed.id bed_id,emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,bed.status app_status  " +
            "from b_ed_application bed " +
            "join inst_employee emp on (emp.id=bed.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where bed.status= ? and institute.id=? order by bed.created_date desc ", status, instituteId);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }
   /* public List<Map<String, Object>> findGeneralAppList () {
        List<Map<String, Object>>
            //instituteList = this.jdbcTemplate.queryForList("select id, inst_name,apply_date from iams_app_form where save_status = 1 OFFSET ? ROWS FETCH NEXT ? ROWS ONLY order by id desc", offset, limit);
            instituteList = this.jdbcTemplate.queryForList("select id, auth_name,code,edition,isbn_no,content_image_content_type,content_image_name from dl_cont_upld where status = 3 order by id desc");

        if(instituteList !=null && instituteList.size()>0){
            return instituteList;
        }else{
            return new ArrayList<>();
        }
    }*/


    public List<Map<String, Object>> findBEdApprovedListByInstituteId(Long instituteId, int status) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select bed.id bed_id,emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,bed.status app_status  " +
            "from b_ed_application bed " +
            "join inst_employee emp on (emp.id=bed.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where bed.status >= ? and institute.id=? order by bed.created_date desc ", status, instituteId);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    public List<Map<String, Object>> findBEdApprovedListByDistrictId(Long districtId, int status) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select bed.id bed_id,emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,bed.status app_status  " +
            "from b_ed_application bed " +
            "join inst_employee emp on (emp.id=bed.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where bed.status >= ? and upazila.district_id = ? order by bed.created_date desc  ", status, districtId);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    public List<Map<String, Object>> findCurriculumByInstId(String userName) {
        List<Map<String, Object>>
            findCurriculumByInstId = this.jdbcTemplate.queryForList(
            "SELECT II.ID,II.FIRST_DATE,II.LAST_DATE,II.MPO_ENLISTED, II.REC_NO, II.MPO_DATE, CM.NAME " +
                "FROM iis_curriculum_info II, institute IT, cms_curriculum CM, JHI_USER " +
                "WHERE ii.cms_curriculum_id = CM.id " +
                "AND ii.institute_id=IT.ID  " +
                "AND IT.USER_ID=JHI_USER.ID " +
                "AND JHI_USER.LOGIN = ?", userName);
        if (findCurriculumByInstId != null && findCurriculumByInstId.size() > 0) {
            return findCurriculumByInstId;
        } else {
            return new ArrayList<>();
        }
    }

    public List<Map<String, Object>> findCurriculumTempByInstId(String userName) {
        List<Map<String, Object>>
            findCurriculumByInstId = this.jdbcTemplate.queryForList(
            "SELECT II.ID,II.FIRST_DATE,II.LAST_DATE,II.MPO_ENLISTED, II.REC_NO, II.MPO_DATE, CM.NAME " +
                "FROM iis_curriculum_info_temp II, institute IT, cms_curriculum CM, JHI_USER " +
                "WHERE ii.CMSCURRICULUM_ID = CM.id " +
                "AND ii.institute_id=IT.ID  " +
                "AND IT.USER_ID=JHI_USER.ID " +
                "AND JHI_USER.LOGIN = ?", userName);
        if (findCurriculumByInstId != null && findCurriculumByInstId.size() > 0) {
            return findCurriculumByInstId;
        } else {
            return new ArrayList<>();
        }
    }

    public List<Map<String, Object>> findDlBookInfosByInstId(String userName) {
        List<Map<String, Object>>
            findDlBookInfosByInstId = this.jdbcTemplate.queryForList(
            "SELECT d.id, d.book_img_content_type,d.book_img_name,d.book_img,d.author_name, " +
                "d.call_no, d.isbn_no, d.title, d.edition " +
                "FROM dl_book_info d, JHI_USER, institute " +
                "WHERE d.institute_id = institute.id AND institute.USER_ID = JHI_USER.ID AND JHI_USER.LOGIN = ?", userName);
        if (findDlBookInfosByInstId != null && findDlBookInfosByInstId.size() > 0) {
            return findDlBookInfosByInstId;
        } else {
            return new ArrayList<>();
        }
    }

    public List<Map<String, Object>> findIssueInfoByid(Long id) {
        List<Map<String, Object>> result = this.jdbcTemplate.queryForList(
            "SELECT dlBinfo.title as bName,dlBinfo.author_name as aName,dlBinfo.compensation as compensation,dlBinfo.edition,dlBinfo.isbn_no,dlBinfo.id,DlIssu.no_of_copies,DlIssu.return_date,DlIssu.issue_date,SisStuInfo.name as Sname,SisStuInfo.email_address as Semail,SisStuInfo.father_name as Sfname,SisStuInfo.gender as Sgender,SisStuInfo.mobile_no as Smno,SisStuInfo.shift as Sshift,fine.FINE,fine.TIME_LIMIT " +
                "FROM dl_book_issue DlIssu,dl_book_info dlBinfo,sis_student_info SisStuInfo,inst_employee instEmpl,DL_FINE_SET_UP fine " +
                "WHERE DlIssu.isbn_no = dlBinfo.isbn_no " +
                "AND DlIssu.status = 1  " +
                "AND dlBinfo.DLCONTSCATSET_ID=fine.DL_CONT_S_CAT_SET_ID " +
                "AND DlIssu.sis_student_info_id = SisStuInfo.id  " +
                "AND DlIssu.id = ?", id);
        if (result != null && result.size() > 0) {
            return result;
        } else {
            return new ArrayList<>();
        }

    }/*public List<Map<String, Object>> findIssueInfoByid (Long id) {
        List<Map <String, Object>> result =  this.jdbcTemplate.queryForList(
            "SELECT InstEmp.name as instName,dlBinfo.title as bName,dlBinfo.id,DlIssu.no_of_copies,DlIssu.return_date,SisStuInfo.name as Sname,SisStuInfo.email_address as Semail,SisStuInfo.father_name as Sfname,SisStuInfo.gender as Sgender,SisStuInfo.mobile_no as Smno,SisStuInfo.shift as Sshift " +
                "FROM dl_book_issue DlIssu,dl_book_info dlBinfo,inst_employee InstEmp sis_student_info SisStuInfo " +
                "WHERE DlIssu.isbn_no = dlBinfo.isbn_no " +
                "AND DlIssu.inst_employee_code = InstEmp.code  " +
                "AND DlIssu.status = 1  " +
                "AND SisStuInfo.sis_student_info_id = SisStuInfo.id  " +
                "AND DlIssu.id = ?", id);
        if(result !=null && result.size()>0){
            return result;
        }else{
            return new ArrayList<>();
        }


    }*/


    public List<Map<String, Object>> findCourseByInstId(String userName) {
        List<Map<String, Object>>
            findCourseByInstId = this.jdbcTemplate.queryForList(
            "SELECT IIC.ID,IIC.PER_DATE_BTEB,IIC.SEAT_NO,CT.NAME as CTM, CM.NAME " +
                "FROM iis_curriculum_info II,iis_course_info IIC,cms_trade CT, institute IT, cms_curriculum CM, JHI_USER " +
                "WHERE iic.IIS_CURRICULUM_INFO_ID = II.id " +
                "AND ii.cms_curriculum_id = CM.id  " +
                "AND iic.CMS_TRADE_ID = CT.id  " +
                "AND iic.institute_id=IT.ID  " +
                "AND IT.USER_ID=JHI_USER.ID " +
                "AND JHI_USER.LOGIN = ?", userName);
        if (findCourseByInstId != null && findCourseByInstId.size() > 0) {
            return findCourseByInstId;
        } else {
            return new ArrayList<>();
        }
    }

    // New version for Temp Implementation of Course Approval

    public List<Map<String, Object>> findCourseByInstIdfromTemp(String userName) {
        List<Map<String, Object>>
            findCourseByInstId = this.jdbcTemplate.queryForList(
            "SELECT IIC.ID,IIC.PER_DATE_BTEB,IIC.SEAT_NO,CT.NAME as CTM, CM.NAME " +
                "FROM iis_curriculum_info_temp II," +
                "iis_course_info IIC," +
                "cms_trade CT, " +
                "institute IT, " +
                "cms_curriculum CM, " +
                "JHI_USER " +
                "WHERE iic.IISCURRICULUMINFO_ID = II.id " +
                "AND iic.cms_curriculum_id = CM.id  " +
                "AND iic.CMSTRADE_ID = CT.id  " +
                "AND iic.institute_id=IT.ID  " +
                "AND IT.USER_ID=JHI_USER.ID " +
                "AND JHI_USER.LOGIN = ?", userName);
        if (findCourseByInstId != null && findCourseByInstId.size() > 0) {
            return findCourseByInstId;
        } else {
            return new ArrayList<>();
        }
    }


    public List<Map<String, Object>> findBEdPendingListForAdmin(int status) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select bed.id bed_id,emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,bed.status app_status  " +
            "from b_ed_application bed " +
            "join inst_employee emp on (emp.id=bed.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where bed.status < ? order by bed.created_date desc  ", status);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }


    public Map<String, Object> getTotalExperienceJP(Long employeeId) {
        Map<String, Object> map = null;

        map.put("experience", jdbcTemplate.queryForMap("select round((trunc(sysdate,'rrrr')- trunc(min(START_FROM),'rrrr')) /365,1) " +
            "from JP_EMPLOYMENT_HISTORY " +
            "group by jp_employee_id " +
            "where jp_employee_id = ? ", employeeId));

        return map;

        /*if(list !=null && list.size()>0){
            return list;
        }else{
            return new ArrayList<>();
        }*/

    }


    public List<Map<String, Object>> findInfoCorrectionApprovedListByInstituteId(Long instituteId, int status) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select info.id info_id, info.created_date app_date, emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,mpo.status app_status  " +
            "from info_correction info " +
            "join inst_employee emp on (emp.id=info.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where info.status >= ? and institute.id=? ", status, instituteId);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    public List<Map<String, Object>> findInfoCorrectionPendingListByInstituteId(Long instituteId, int status) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select info.id info_id, info.created_date app_date, emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name,info.status app_status  " +
            "from info_correction info " +
            "join inst_employee emp on (emp.id=info.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where info.status = ? and institute.id=? ", status, instituteId);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }


    public List<Map<String, Object>> findInfoCorrectionPendingList(int status) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select info.id info_id, info.created_date app_date, emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name, info.status app_status  " +
            "from info_correction info " +
            "join inst_employee emp on (emp.id=info.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where info.status = ? ", status);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    public List<Map<String, Object>> findInfoCorrectionApprovedList(int status) {
        List<Map<String, Object>> list = null;

        list = jdbcTemplate.queryForList("select info.id info_id, info.created_date app_date, emp.code, emp.teacher_level,institute.name inst_name, " +
            "upazila.name upzila_name, info.status app_status  " +
            "from info_correction info " +
            "join inst_employee emp on (emp.id=info.inst_employee_id) " +
            "join institute on (institute.id=emp.institute_id) " +
            "join upazila on (upazila.id=institute.upazila_id) " +
            "where info.status >= ? ", status);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }


    public void getSortedCv(String location, int age, String applicant_name, String gender,
                            int exprience, int salary_range, String applicant_name2, String
        district_name, int age2, int expected_salary)
        throws Exception {
        Connection c = jdbcTemplate.getDataSource().getConnection();
        CallableStatement cs = null;
        cs = c.prepareCall("{call job_emp_dtl(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
        cs.setString(1, location);
        cs.setInt(2, age);
        cs.setString(3, applicant_name);
        cs.setString(4, gender);
        cs.setInt(5, exprience);
        cs.setInt(6, salary_range);
        cs.setString(7, applicant_name2);
        cs.setString(8, district_name);
        cs.setInt(9, age2);
        cs.setInt(10, expected_salary);
        cs.execute();
    }

    //ris start
    public List<Map<String, Object>> smsWritten(Long seat) {
        List<Map<String, Object>> list = null;

        list = this.jdbcTemplate.queryForList("select * from ris_new_app_form where rownum <=?", seat);

        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

   /* public List<Map<String, Object>> driverassignemployeebydepartment(String id) {
        List<Map<String, Object>> list = null;
        System.out.println("inside driverassignemployeebydepartment rptjdbc ");
        String query = "select * from HR_EMPLOYEE_INFO right join hr_department_setup on hr_department_setup.id= HR_EMPLOYEE_INFO.DEPARTMENT_INFO_ID right join hr_department_head_setup on hr_department_head_setup.id = hr_department_setup.DEPARTMENT_INFO_ID where hr_department_head_setup.id="+id;
        list = this.jdbcTemplate.queryForList(query);

        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }*/

    public List<Map<String, Object>> seatwithcircular(Long seat, String circular) {
        List<Map<String, Object>> list = null;

        list = this.jdbcTemplate.queryForList("select * from ris_new_app_form where rownum <=" + seat + "and CIRCULAR_NO ='" + circular + "'");

        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    public List<Map<String, Object>> positionbycircular(String circular) {
        List<Map<String, Object>> list = null;

        list = this.jdbcTemplate.queryForList("select POSITION from ris_new_job_posting where CIRCULARNO= '" + circular + "'");

        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }
    public List<Map<String, Object>> findingTotalEmployed() {
        List<Map<String, Object>>
            /*findingTotalEmployed = this.jdbcTemplate.queryForList("select HR_DESIGNATION_HEAD_SETUP.DESIGNATION_NAME, HR_DEPARTMENT_HEAD_SETUP.DEPARTMENT_NAME, HR_DESIGNATION_SETUP.ELOCATTED_POSITION as allocated, count (HR_EMPLOYEE_INFO.DESIGNATION_INFO_ID) as Assigned, (HR_DESIGNATION_SETUP.ELOCATTED_POSITION-count(HR_EMPLOYEE_INFO.DESIGNATION_INFO_ID)) as vacant from HR_EMPLOYEE_INFO right join HR_DEPARTMENT_SETUP on HR_DEPARTMENT_SETUP.id =HR_EMPLOYEE_INFO.DEPARTMENT_INFO_ID right join HR_DEPARTMENT_HEAD_SETUP on HR_DEPARTMENT_HEAD_SETUP.id = HR_DEPARTMENT_SETUP.DEPARTMENT_INFO_ID right join HR_DESIGNATION_SETUP on HR_DESIGNATION_SETUP.id =HR_EMPLOYEE_INFO.DESIGNATION_INFO_ID right join HR_DESIGNATION_HEAD_SETUP on HR_DESIGNATION_SETUP.DESIGNATION_INFO_ID =HR_DESIGNATION_HEAD_SETUP.id group by HR_EMPLOYEE_INFO.DESIGNATION_INFO_ID, HR_DESIGNATION_HEAD_SETUP.DESIGNATION_NAME, HR_DEPARTMENT_HEAD_SETUP.DEPARTMENT_NAME, HR_DESIGNATION_SETUP.ELOCATTED_POSITION");*/
           /* findingTotalEmployed = this.jdbcTemplate.queryForList("select HR_DESIGNATION_HEAD_SETUP.DESIGNATION_NAME, HR_DEPARTMENT_HEAD_SETUP.DEPARTMENT_NAME, HR_DESIGNATION_SETUP.ELOCATTED_POSITION as allocated,count (HR_EMPLOYEE_INFO.DESIGNATION_INFO_ID) as Assigned, (HR_DESIGNATION_SETUP.ELOCATTED_POSITION-count(HR_EMPLOYEE_INFO.DESIGNATION_INFO_ID)) as vacant,hr_grade_setup.grade_detail from HR_EMPLOYEE_INFO right join HR_DEPARTMENT_SETUP on HR_DEPARTMENT_SETUP.id =HR_EMPLOYEE_INFO.DEPARTMENT_INFO_ID right join HR_DEPARTMENT_HEAD_SETUP on HR_DEPARTMENT_HEAD_SETUP.id = HR_DEPARTMENT_SETUP.DEPARTMENT_INFO_ID right join HR_DESIGNATION_SETUP on HR_DESIGNATION_SETUP.id =HR_EMPLOYEE_INFO.DESIGNATION_INFO_ID right join HR_DESIGNATION_HEAD_SETUP on HR_DESIGNATION_SETUP.DESIGNATION_INFO_ID =HR_DESIGNATION_HEAD_SETUP.id right join hr_grade_setup on hr_grade_setup.id =HR_DESIGNATION_SETUP.GRADE_INFO_ID where hr_grade_setup.grade_code in ('G-11','G-12','G-13','G-14','G-15','G-16','G-17','G-18','G-19','G-20') group by HR_EMPLOYEE_INFO.DESIGNATION_INFO_ID, HR_DESIGNATION_HEAD_SETUP.DESIGNATION_NAME, HR_DEPARTMENT_HEAD_SETUP.DEPARTMENT_NAME, HR_DESIGNATION_SETUP.ELOCATTED_POSITION,hr_grade_setup.grade_detail");*/
            /*findingTotalEmployed = this.jdbcTemplate.queryForList("select HR_DESIGNATION_HEAD_SETUP.ID,HR_DESIGNATION_HEAD_SETUP.DESIGNATION_NAME ,HR_DEPARTMENT_HEAD_SETUP.DEPARTMENT_NAME, HR_DESIGNATION_SETUP.ELOCATTED_POSITION as allocated,count (HR_EMPLOYEE_INFO.DESIGNATION_INFO_ID) as Assigned, (HR_DESIGNATION_SETUP.ELOCATTED_POSITION-count(HR_EMPLOYEE_INFO.DESIGNATION_INFO_ID)) as vacant,hr_grade_setup.grade_detail from HR_EMPLOYEE_INFO right join HR_DEPARTMENT_SETUP on HR_DEPARTMENT_SETUP.id =HR_EMPLOYEE_INFO.DEPARTMENT_INFO_ID right join HR_DEPARTMENT_HEAD_SETUP on HR_DEPARTMENT_HEAD_SETUP.id = HR_DEPARTMENT_SETUP.DEPARTMENT_INFO_ID right join HR_DESIGNATION_SETUP on HR_DESIGNATION_SETUP.id =HR_EMPLOYEE_INFO.DESIGNATION_INFO_ID right join HR_DESIGNATION_HEAD_SETUP on HR_DESIGNATION_SETUP.DESIGNATION_INFO_ID =HR_DESIGNATION_HEAD_SETUP.id right join hr_grade_setup on hr_grade_setup.id =HR_DESIGNATION_SETUP.GRADE_INFO_ID where hr_grade_setup.grade_code in ('G-11','G-12','G-13','G-14','G-15','G-16','G-17','G-18','G-19','G-20') group by HR_EMPLOYEE_INFO.DESIGNATION_INFO_ID, HR_DESIGNATION_HEAD_SETUP.DESIGNATION_NAME, HR_DEPARTMENT_HEAD_SETUP.DEPARTMENT_NAME, HR_DESIGNATION_SETUP.ELOCATTED_POSITION,hr_grade_setup.grade_detail,HR_DESIGNATION_HEAD_SETUP.ID");*/
            /*findingTotalEmployed = this.jdbcTemplate.queryForList("select HR_DESIGNATION_HEAD_SETUP.ID,HR_DESIGNATION_HEAD_SETUP.DESIGNATION_NAME ,HR_DEPARTMENT_HEAD_SETUP.DEPARTMENT_NAME,HR_DESIGNATION_SETUP.ELOCATTED_POSITION as allocated,count (HR_EMPLOYEE_INFO.DESIGNATION_INFO_ID) as Assigned, (HR_DESIGNATION_SETUP.ELOCATTED_POSITION-count(HR_EMPLOYEE_INFO.DESIGNATION_INFO_ID)) as vacant,hr_grade_setup.grade_detail from HR_EMPLOYEE_INFO right join HR_DEPARTMENT_SETUP on HR_DEPARTMENT_SETUP.id =HR_EMPLOYEE_INFO.DEPARTMENT_INFO_ID right join HR_DEPARTMENT_HEAD_SETUP on HR_DEPARTMENT_HEAD_SETUP.id = HR_DEPARTMENT_SETUP.DEPARTMENT_INFO_ID right join HR_DESIGNATION_SETUP on HR_DESIGNATION_SETUP.id =HR_EMPLOYEE_INFO.DESIGNATION_INFO_ID right join HR_DESIGNATION_HEAD_SETUP on HR_DESIGNATION_SETUP.DESIGNATION_INFO_ID =HR_DESIGNATION_HEAD_SETUP.id right join hr_grade_setup on hr_grade_setup.id =HR_DESIGNATION_SETUP.GRADE_INFO_ID where hr_grade_setup.grade_code in ('G-11','G-12','G-13','G-14','G-15','G-16','G-17','G-18','G-19','G-20') and HR_DESIGNATION_HEAD_SETUP.ID not in (select job_id from RIS_NEW_JOB_POSTING where job_id is not null) group by HR_EMPLOYEE_INFO.DESIGNATION_INFO_ID, HR_DESIGNATION_HEAD_SETUP.DESIGNATION_NAME, HR_DEPARTMENT_HEAD_SETUP.DEPARTMENT_NAME, HR_DESIGNATION_SETUP.ELOCATTED_POSITION,hr_grade_setup.grade_detail,HR_DESIGNATION_HEAD_SETUP.ID "); //here i am excluding those designation ids from ris_new_job_posting track with already saved id*/
            /*findingTotalEmployed = this.jdbcTemplate.queryForList("select HR_DESIGNATION_HEAD_SETUP.ID,HR_DESIGNATION_HEAD_SETUP.DESIGNATION_NAME ,HR_DEPARTMENT_HEAD_SETUP.DEPARTMENT_NAME,HR_DESIGNATION_SETUP.ELOCATTED_POSITION as allocated,count (HR_EMPLOYEE_INFO.DESIGNATION_INFO_ID) as Assigned, (HR_DESIGNATION_SETUP.ELOCATTED_POSITION-count(HR_EMPLOYEE_INFO.DESIGNATION_INFO_ID)) as vacant,hr_grade_setup.grade_detail from HR_EMPLOYEE_INFO right join HR_DEPARTMENT_SETUP on HR_DEPARTMENT_SETUP.id =HR_EMPLOYEE_INFO.DEPARTMENT_INFO_ID right join HR_DEPARTMENT_HEAD_SETUP on HR_DEPARTMENT_HEAD_SETUP.id = HR_DEPARTMENT_SETUP.DEPARTMENT_INFO_ID right join HR_DESIGNATION_SETUP on HR_DESIGNATION_SETUP.id =HR_EMPLOYEE_INFO.DESIGNATION_INFO_ID right join HR_DESIGNATION_HEAD_SETUP on HR_DESIGNATION_SETUP.DESIGNATION_INFO_ID =HR_DESIGNATION_HEAD_SETUP.id right join hr_grade_setup on hr_grade_setup.id =HR_DESIGNATION_SETUP.GRADE_INFO_ID where  HR_DESIGNATION_HEAD_SETUP.ID not in (select job_id from RIS_NEW_JOB_POSTING where job_id is not null) group by HR_EMPLOYEE_INFO.DESIGNATION_INFO_ID, HR_DESIGNATION_HEAD_SETUP.DESIGNATION_NAME, HR_DEPARTMENT_HEAD_SETUP.DEPARTMENT_NAME, HR_DESIGNATION_SETUP.ELOCATTED_POSITION,hr_grade_setup.grade_detail,HR_DESIGNATION_HEAD_SETUP.ID "); //without grading filteration*/
            findingTotalEmployed = this.jdbcTemplate.queryForList("select HR_DESIGNATION_HEAD_SETUP.ID as DesignationId,HR_DESIGNATION_HEAD_SETUP.DESIGNATION_NAME ,HR_DEPARTMENT_HEAD_SETUP.DEPARTMENT_NAME,HR_DESIGNATION_SETUP.ELOCATTED_POSITION as allocated,count (HR_EMPLOYEE_INFO.DESIGNATION_INFO_ID) as Assigned, (HR_DESIGNATION_SETUP.ELOCATTED_POSITION-count(HR_EMPLOYEE_INFO.DESIGNATION_INFO_ID)) as vacant,hr_grade_setup.GRADE_CODE from HR_EMPLOYEE_INFO right join HR_DEPARTMENT_SETUP on HR_DEPARTMENT_SETUP.id =HR_EMPLOYEE_INFO.DEPARTMENT_INFO_ID right join HR_DEPARTMENT_HEAD_SETUP on HR_DEPARTMENT_HEAD_SETUP.id = HR_DEPARTMENT_SETUP.DEPARTMENT_INFO_ID right join HR_DESIGNATION_SETUP on HR_DESIGNATION_SETUP.id =HR_EMPLOYEE_INFO.DESIGNATION_INFO_ID right join HR_DESIGNATION_HEAD_SETUP on HR_DESIGNATION_SETUP.DESIGNATION_INFO_ID =HR_DESIGNATION_HEAD_SETUP.id right join hr_grade_setup on hr_grade_setup.id =HR_DESIGNATION_SETUP.GRADE_INFO_ID where hr_grade_setup.grade_code in ('G-11','G-12','G-13','G-14','G-15','G-16','G-17','G-18','G-19','G-20') and HR_DESIGNATION_HEAD_SETUP.ID not in (select job_id from RIS_NEW_JOB_POSTING where job_id is not null) group by HR_EMPLOYEE_INFO.DESIGNATION_INFO_ID, HR_DESIGNATION_HEAD_SETUP.DESIGNATION_NAME, HR_DEPARTMENT_HEAD_SETUP.DEPARTMENT_NAME, HR_DESIGNATION_SETUP.ELOCATTED_POSITION,hr_grade_setup.GRADE_CODE,HR_DESIGNATION_HEAD_SETUP.ID  ");

        if (findingTotalEmployed != null && findingTotalEmployed.size() > 0) {
            return findingTotalEmployed;
        } else {
            return new ArrayList<>();
        }
    }


    public List<Map<String, Object>> findEmployeeById(Long id) {
        List<Map<String, Object>> list = null;

//        list = jdbcTemplate.queryForList("select mpo.id mpo_id,emp.code,institute.name inst_name, " +
//            "upazila.name upzila_name,mpo.status app_status  " +
//            "from mpo_application mpo " +
//            "join inst_employee emp on (emp.id=mpo.inst_employee_id) " +
//            "join institute on (institute.id=emp.institute_id) " +
//            "join upazila on (upazila.id=institute.upazila_id) " +
//            "where mpo.status < ? ",status);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    public List<Map<String, Object>> uniqueCircular(String circular) {
        List<Map<String, Object>> list = null;

        list = this.jdbcTemplate.queryForList("select circularno from RIS_NEW_JOB_POSTING where CIRCULARNO ='" + circular + "'");

        if (list != null && list.size() > 0) {
            System.out.println("Circular Found");
            return list;
        } else {
            System.out.println("Circular Not Found");
            return new ArrayList<>();
        }

    }

    public List<Map<String, Object>> gettingwithcircular(String circular) {
        List<Map<String, Object>> list = null;

        list = this.jdbcTemplate.queryForList("select * from ris_new_app_form where circular_no = '" + circular + "'");

        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    public List<Map<String, Object>> getJobByCircular(String circular) {
        List<Map<String, Object>> list = null;

        list = this.jdbcTemplate.queryForList("select * from ris_new_job_posting where circularno = '" + circular + "'");

        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    public List<Map<String, Object>> getJobByCircularStatus(Integer status) {
        List<Map<String, Object>> list = null;

        list = this.jdbcTemplate.queryForList("select DISTINCT ris_new_job_posting.CIRCULARNO, ris_new_job_posting_track.DATES from ris_new_job_posting right join ris_new_job_posting_track on ris_new_job_posting.id = ris_new_job_posting_track.RIS_NEW_JOB_POSTING_ID where ris_new_job_posting_track.status=" + status);

        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    public List<Map<String, Object>> getApplicantByRegNo(Long regno, Integer status) {
        List<Map<String, Object>> list = null;

        list = this.jdbcTemplate.queryForList("select * from ris_new_app_form where reg_no =" + regno + "and application_status=" + status);

        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    public List<Map<String, Object>> getJobCircular() {
        List<Map<String, Object>> list = null;

        list = this.jdbcTemplate.queryForList("select distinct circularno from ris_new_job_posting");

        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    public List<Map<String, Object>> getAllVclVehicleTypesbyStatus() {
        List<Map<String, Object>> list = null;

        list = this.jdbcTemplate.queryForList("select * from vcl_vehicle_type where active_status = 1");

        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }


    //for using where in to return necessary fields for sms and email send
    public List<Map<String, Object>> smsandemail(String id) {
        List<Map<String, Object>> list = null;
        System.out.println("<<<<<<<<<<  RPTJDBC id  >>>>>>>>>>>>>>");
        System.out.println("ids getting in rpt " + id);
        String query = "select APPLICANTS_NAME_EN,CONTACT_PHONE,EMAIL,DESIGNATION from ris_new_app_form where REG_NO in (" + id + ")";
        System.out.println("The Query Is " + query);
        list = this.jdbcTemplate.queryForList(query);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }


    }

    //for dash board
    public List<Map<String, Object>> applicantStatus(Long id) {
        List<Map<String, Object>> list = null;
        System.out.println("ids getting in rpt " + id);
        String query = "select APPLICANTS_NAME_EN,CIRCULAR_NO, DESIGNATION, WRITTEN_MARKS, VIVA_MARKS, REG_NO from RIS_NEW_APP_FORM where CREATE_BY = " + id;
        System.out.println("The Query Is " + query);
        list = this.jdbcTemplate.queryForList(query);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }


    }


    //modular api for updating ids with corresponded status
    public void updateForWrittenExam(String id, String status, String venueName, String examDate, String examTime) {

        System.out.println("<<<<<<<<<<  RPTJDBC id updateForWrittenExam >>>>>>>>>>>>>>");
        System.out.println("\n registartion number getting in rpt updateForWrittenExam " + id);
        System.out.println("exam Date received" + examDate);
        String query = "update ris_new_app_form set application_status =" + status + " where REG_NO in (" + id + ")";
        System.out.println("\n The Query for updating written exam status " + query);
        int update = this.jdbcTemplate.update(query);
        System.out.println("update :" + update);
       /*inserting data into RIS_NEW_EXAM_STATUS*/
        String[] ids = id.split(",");
        for (String w : ids) {
            System.out.println("ids after split :" + w);
            String s = "INSERT INTO RIS_NEW_EXAM_STATUS (VENUE_NAME, EXAM_DATE, EXAM_TIME, STATUS, ID) VALUES ('" + venueName + "', TO_DATE('" + examDate + "', 'DD-MON-RR'), '" + examTime + "', '" + status + "', '" + w + "')";
            System.out.println("query for inserting :" + s);
            int insert = this.jdbcTemplate.update(s);
            System.out.println("insert :" + insert);
        }
    }


    //modular api for updating ids with corresponded status
    public void updateforAppointmentLetter(String id, String status) {

        System.out.println("<<<<<<<<<<  RPTJDBC id updateForWrittenExam >>>>>>>>>>>>>>");
        System.out.println("\n registartion number getting in rpt updateForWrittenExam " + id);

        String query = "update ris_new_app_form set application_status =" + status + " where REG_NO =" + id;
        System.out.println("\n The Query for updating appointment letter issued status " + query);
        int update = this.jdbcTemplate.update(query);
        System.out.println("update :" + update);


        //may need to updated for record when the appointment letter is issued

       /*inserting data into RIS_NEW_EXAM_STATUS*//*
        String[] ids=id.split(",");
        for(String w:ids){
            System.out.println("ids after split :"+w);
            String s = "INSERT INTO RIS_NEW_EXAM_STATUS (VENUE_NAME, EXAM_DATE, EXAM_TIME, STATUS, ID) VALUES ('"+venueName+"', TO_DATE('"+examDate+"', 'DD-MON-RR'), '"+examTime+"', '"+status+"', '"+w+"')";
            System.out.println("query for inserting :"+s);
            int insert = this.jdbcTemplate.update(s);
            System.out.println("insert :"+insert);
        }*/
    }

    public int writtenexamentry(String regno, String marks) {


        String query = "update ris_new_app_form set WRITTEN_MARKS =" + marks + " where REG_NO =" + regno;
        System.out.println("\n The Query for inserting written marks to an applicants profile  " + query);
        int update = this.jdbcTemplate.update(query);
        System.out.println("inserted  :" + update);
        return 1;
    }

    public int vivamarksentry(String regno, String marks) {
        String query = "update ris_new_app_form set VIVA_MARKS =" + marks + " where REG_NO =" + regno;
        System.out.println("\n The Query for inserting VIVA_MARKS to an applicants profile  " + query);
        int update = this.jdbcTemplate.update(query);
        System.out.println("inserted  :" + update);
        return 1;
    }


    //updating status for the selected applicants
    public void selection(String regNo, String status) {

        String query = "update ris_new_app_form set application_status =" + status + " where REG_NO in (" + regNo + ")";
        System.out.println("\n The Query for updating exam selection" + query);
        int update = this.jdbcTemplate.update(query);
        System.out.println("update :" + update);
    }


    //jobRequest controller
    //from git

    //query for posting job to portal
    public void putjobRequest(String position, String department, String allocated, String currentEmp, String availVac, String status, String circularNo, String jobId) {

        /*String query = "INSERT INTO RIS_NEW_JOB_POSTING (POSITION,DEPARTMENT,CURRENT_EMPLOYEE,AVAILABLE_POSITIONS,STATUS) values (\" "+position+"\",\""+department+"\","+currentEmp+","+availVac+","+status+")";*/

        List<Map<String, Object>> ids = null;
        List<String> idget = new ArrayList<>();
        String pattern = "dd-MMM-yy";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        String day = format.format(new Date());
        /*String query = "INSERT INTO RIS_NEW_JOB_POSTING (POSITION,DEPARTMENT,ALLOCATED,CURRENT_EMPLOYEE,AVAILABLE_POSTINGS,CIRCULARNO) values ('"+position+"','"+department+"','"+allocated+"','"+currentEmp+"','"+availVac+"','"+circularNo+"'"+")";*/
        String query = "INSERT INTO RIS_NEW_JOB_POSTING (POSITION,DEPARTMENT,ALLOCATED,CURRENT_EMPLOYEE,AVAILABLE_POSTINGS,CIRCULARNO,JOB_ID) values ('" + position + "','" + department + "'," + allocated + "," + currentEmp + "," + availVac + ",'" + circularNo + "'," + jobId + ")";
        //need to insert the RIS_NEW_JOB_POSTING id to  RIS_NEW_JOB_POSTING_TRACK
        int update = this.jdbcTemplate.update(query);
        System.out.println(" RIS_NEW_JOB_POSTING inserted >>>>>>>>>> :" + update);


        String qw = "select id from ris_new_job_posting where circularno = '" + circularNo + "'";
        System.out.println("\nThe Query for updating job request by id :" + qw);
        ids = this.jdbcTemplate.queryForList(qw);
        List<Map<String, Object>> maplist;
        Map map = new HashMap<String, Integer>();

        System.out.println(map.get("id"));
        StringBuilder sb = new StringBuilder();
        sb.append("");
        System.out.println("jobRequestUpdate :" + ids.toString());

        for (Map<String, Object> maps : ids) {
            for (Map.Entry<String, Object> entry : maps.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                System.out.println(key);
                System.out.println(value.toString());
                String kk = value.toString();
                idget.add(kk);
                sb.append(value.toString());
                sb.append(",");
            }
        }

        System.out.println("size of the array " + idget.size());
        //plan to pass the ids each time
        for (int i = 0; i < idget.size(); i++) {
            String id = idget.get(i);
            System.out.println("id form list " + id);
            String q = "INSERT INTO RIS_NEW_JOB_POSTING_TRACK (STATUS,DATES,RIS_NEW_JOB_POSTING_ID) values ('" + status + "','" + day + "'," + id + ")";
            System.out.println("Query was:" + q);
            int update21 = this.jdbcTemplate.update(q);
            System.out.println("inserted also into RIS_NEW_JOB_POSTING_TRACK with this query >>>  :" + update21);

        }

        System.out.println("ids to be updated" + sb.toString().substring(0, sb.toString().length() - 1));
        /*String q = "update ris_new_job_posting_track set status=" + status + " where id in (" + sb.toString().substring(0, sb.toString().length() - 1) + ")";*/

        /*String q = "INSERT INTO RIS_NEW_JOB_POSTING_TRACK (STATUS,DATES,RIS_NEW_JOB_POSTING_ID) values ('" + status + "','" + day + "'," + sb.toString().substring(0, sb.toString().length() - 1) + ")";

        System.out.println("Query was:" + q);
        int update21 = this.jdbcTemplate.update(q);
        System.out.println("inserted also into RIS_NEW_JOB_POSTING_TRACK with this query >>>  :" + update21);*/

        /*String query2 = "INSERT INTO RIS_NEW_JOB_POSTING_TRACK (STATUS,DATES) values ('" + status + "','" + day + "')";
        System.out.println("\nThe Query for inserting values into RIS_NEW_JOB_POSTING_track " + query2);
        System.out.println("\nThe Query for inserting values into RIS_NEW_JOB_POSTING " + query);

        int update2 = this.jdbcTemplate.update(query2);

        System.out.println("update posting track :" + update2);*/
    }

    //query for saving job information posting job to portal or website
    /*public void jobPosting(String risCircularNo, String EDUCATIONAL_QUALIFICATION, String OTHER_QUALIFICATION, String REMARKS, String PUBLISH_DATE, String APPLICATION_DATE, String ATTACHMENT, String ATTACHMENT_CONTENT_TYPE, String CREATED_DATE, String UPDATED_DATE, String STATUS, String ATTACHMENT_IMG_NAME, String POSITION_NAME, String VACANT_POSITIONS, String DEPARTMENT) {*/
    public void jobPosting(RisJobPostingDTO risJobPostingDTO) {

        /*String query = "INSERT INTO RIS_NEW_JOB_POSTING (POSITION,DEPARTMENT,CURRENT_EMPLOYEE,AVAILABLE_POSITIONS,STATUS) values (\" "+position+"\",\""+department+"\","+currentEmp+","+availVac+","+status+")";*/
        Integer statusint = 0;
        Integer vacantpos = 0;
        String PUBLISH_DATEs = null;
        String APPLICATION_DATEs = null;
        String CREATED_DATEs = null;
        String UPDATED_DATEs = null;
        Long CREATED_BY = SecurityUtils.getCurrentUserId();
        Long UPDATED_BY = SecurityUtils.getCurrentUserId();
        if (risJobPostingDTO.getStatus() != null && !risJobPostingDTO.getStatus().isEmpty()) {
            statusint = Integer.parseInt(risJobPostingDTO.getStatus());
        }
        if (risJobPostingDTO.getVacantPosition() != null && !risJobPostingDTO.getVacantPosition().isEmpty()) {
            statusint = Integer.parseInt(risJobPostingDTO.getVacantPosition());
        }

        try {

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date dd = df.parse(risJobPostingDTO.getPublishDate());

            DateFormat dfs = new SimpleDateFormat("dd/MMM/yyyy");
            PUBLISH_DATEs = dfs.format(dd);
            System.out.println("converting to date " + PUBLISH_DATEs);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        try {

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date dd = df.parse(risJobPostingDTO.getCreatedDate());
            DateFormat dfs = new SimpleDateFormat("dd/MMM/yyyy");
            CREATED_DATEs = dfs.format(dd);
            System.out.println("converting to date " + CREATED_DATEs);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date dd = df.parse(risJobPostingDTO.getUpdatedDate());
            DateFormat dfs = new SimpleDateFormat("dd/MMM/yyyy");
            UPDATED_DATEs = dfs.format(dd);
            System.out.println("converting to date " + UPDATED_DATEs);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date dd = df.parse(risJobPostingDTO.getApplicationDate());
            DateFormat dfs = new SimpleDateFormat("dd/MMM/yyyy");
            APPLICATION_DATEs = dfs.format(dd);
            System.out.println("converting to date " + APPLICATION_DATEs);
        } catch (ParseException e) {
            e.printStackTrace();
        }



        /*String query = "INSERT INTO RIS_NEW_JOB_POSTING (POSITION,DEPARTMENT,ALLOCATED,CURRENT_EMPLOYEE,AVAILABLE_POSTINGS,CIRCULARNO) values ('"+position+"','"+department+"','"+allocated+"','"+currentEmp+"','"+availVac+"','"+circularNo+"'"+")";*/
       /* String query = "INSERT INTO RIS_NEW_VACANCY_NEW (EDUCATIONAL_QUALIFICATION,OTHER_QUALIFICATION,REMARKS,PUBLISH_DATE,APPLICATION_DATE,ATTACHMENT,ATTACHMENT_CONTENT_TYPE,CREATED_DATE,UPDATED_DATE,CREATED_BY,UPDATED_BY,STATUS,ATTACHMENT_IMG_NAME,POSITION_NAME,VACANT_POSITIONS,CIRCULAR_NO) values ('"+EDUCATIONAL_QUALIFICATION+"','"+OTHER_QUALIFICATION+"','"+REMARKS+"',"+PUBLISH_DATE+","+APPLICATION_DATE+",'"+ATTACHMENT+"','"+ATTACHMENT_CONTENT_TYPE+ "',"+CREATED_DATE+","+UPDATED_DATE+","+CREATED_BY+","+UPDATED_BY+","+STATUS+",'"+ATTACHMENT_IMG_NAME+"',"+VACANT_POSITIONS+",'"+risCircularNo+"')";*/
       /* String query = "INSERT INTO RIS_NEW_VACANCY_NEW (EDUCATIONAL_QUALIFICATION,OTHER_QUALIFICATION,REMARKS,PUBLISH_DATE,APPLICATION_DATE,ATTACHMENT,ATTACHMENT_CONTENT_TYPE,CREATED_DATE,UPDATED_DATE,CREATED_BY,UPDATED_BY,STATUS,ATTACHMENT_IMG_NAME,POSITION_NAME,VACANT_POSITIONS,CIRCULAR_NO,DEPARTMENT) values" +
            " ('" + EDUCATIONAL_QUALIFICATION + "','" + OTHER_QUALIFICATION + "','" + REMARKS + "','" + PUBLISH_DATEs + "','" + APPLICATION_DATEs + "','" + ATTACHMENT + "','" + ATTACHMENT_CONTENT_TYPE + "','" + CREATED_DATEs + "','" + UPDATED_DATEs + "'," + CREATED_BY + "," + UPDATED_BY + "," + statusint + ",'" + ATTACHMENT_IMG_NAME + "','" + POSITION_NAME + "'," + vacantpos + ",'" + risCircularNo + "','"+DEPARTMENT+"')";*/


        String query = "INSERT INTO RIS_NEW_VACANCY_NEW (EDUCATIONAL_QUALIFICATION,OTHER_QUALIFICATION,REMARKS,PUBLISH_DATE,APPLICATION_DATE,ATTACHMENT_CONTENT_TYPE,CREATED_DATE,UPDATED_DATE,CREATED_BY,UPDATED_BY,STATUS,ATTACHMENT_IMG_NAME,POSITION_NAME,VACANT_POSITIONS,CIRCULAR_NO,DEPARTMENT) values" +
            " ('" + risJobPostingDTO.getEducationalQualification() + "','" + risJobPostingDTO.getOtherQualification() + "','" + risJobPostingDTO.getRemarks() + "','" + PUBLISH_DATEs + "','" + APPLICATION_DATEs + "','" + risJobPostingDTO.getAttachmentContentType() + "','" + CREATED_DATEs + "','" + UPDATED_DATEs + "'," + CREATED_BY + "," + UPDATED_BY + "," + statusint + ",'" + risJobPostingDTO.getAttachmentImageName() + "','" + risJobPostingDTO.getPositionName() + "'," + vacantpos + ",'" + risJobPostingDTO.getCircularNo() + "','" + risJobPostingDTO.getDepartment() + "')";
        System.out.println("RIS_NEW_VACANCY_NEW query >>>>>>>>>>>=======" + query);
        int update = this.jdbcTemplate.update(query);
        System.out.println("RIS_NEW_VACANCY_NEW :" + update);
    }


    //query for getting job from postal

    public List<Map<String, Object>> getjobPosting(String risCircularNo) {
        List<Map<String, Object>> list = null;
        String query = "select * from RIS_NEW_VACANCY_NEW where circular_no = '" + risCircularNo + "'";
        System.out.println("RIS_NEW_VACANCY_NEW query >>>>>>>>>>>=======" + query);
        list = this.jdbcTemplate.queryForList(query);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }


    //getting all from job portal >>>>.. getting the list
    public List<Map<String, Object>> getalljobPosting() {
        List<Map<String, Object>> list = null;
        String query = "select * from RIS_NEW_VACANCY_NEW";
        System.out.println("RIS_NEW_VACANCY_NEW query >>>>>>>>>>>=======" + query);
        list = this.jdbcTemplate.queryForList(query);
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }


  /*  public void putjobRequest(String position,String department,String allocated,String currentEmp, String availVac, String status, String circularNo ) {

        *//*String query = "INSERT INTO RIS_NEW_JOB_POSTING (POSITION,DEPARTMENT,CURRENT_EMPLOYEE,AVAILABLE_POSITIONS,STATUS) values (\" "+position+"\",\""+department+"\","+currentEmp+","+availVac+","+status+")";*//*


        String pattern = "dd-MMM-yy";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        String day = format.format(new Date());
        *//*String query = "INSERT INTO RIS_NEW_JOB_POSTING (POSITION,DEPARTMENT,ALLOCATED,CURRENT_EMPLOYEE,AVAILABLE_POSTINGS,CIRCULARNO) values ('"+position+"','"+department+"','"+allocated+"','"+currentEmp+"','"+availVac+"','"+circularNo+"'"+")";*//*
        String query = "INSERT INTO RIS_NEW_JOB_POSTING (POSITION,DEPARTMENT,ALLOCATED,CURRENT_EMPLOYEE,AVAILABLE_POSTINGS,CIRCULARNO) values ('"+position+"','"+department+"',"+allocated+","+currentEmp+","+availVac+",'"+circularNo+"'"+")";
        *//*int update = this.jdbcTemplate.update(query);*//*

        String queryforId = "select id from ris_new_job_posting where circularno ='"+circularNo +"'";
        System.out.println("***//*///*/

    /**
     * ** Query code for getting id using circular no : "+queryforId);
     * Map map = new HashMap<String,Integer>();
     * map=  this.jdbcTemplate.queryForMap(queryforId);
     * System.out.println(map.get("id"));
     * int getid = this.jdbcTemplate.update(query);
     * System.out.println("Get id "+getid);
     *//* String query2 = "INSERT INTO RIS_NEW_JOB_POSTING_TRACK (STATUS,DATES,RIS_NEW_JOB_POSTING_ID) values ('"+status+"','"+day+"')";*//*

      *//*  System.out.println("\nThe Query for inserting values into RIS_NEW_JOB_POSTING_track " + query2);*//*
        System.out.println("\nThe Query for inserting values into RIS_NEW_JOB_POSTING " + query);

        *//*int update2 = this.jdbcTemplate.update(query2);*//*
        *//*System.out.println("update posting:"+update);*//*
        *//*System.out.println("update posting track :"+update2);*//*
    }*/

    //jobRequest update status controller minister 2 , ar initial 1 denied by minister 9
    public void jobRequestUpdate(String position, String department, Integer status, String circularNo) {

        /*String query = "INSERT INTO RIS_NEW_JOB_POSTING (POSITION,DEPARTMENT,CURRENT_EMPLOYEE,AVAILABLE_POSITIONS,STATUS) values (\" "+position+"\",\""+department+"\","+currentEmp+","+availVac+","+status+")";*/

        /*String query = "update RIS_NEW_JOB_POSTING right join ris_new_job_posting_track on ris_new_job_posting_track.id =RIS_NEW_JOB_POSTING.id set ris_new_job_posting_track.STATUS = "+status+" where RIS_NEW_JOB_POSTING.POSITION='"+position+"'"+" and RIS_NEW_JOB_POSTING.DEPARTMENT='"+department+"'";*/
        System.out.println("===========" + position + "==============");
        System.out.println("===========" + department + "==============");
        System.out.println("===========" + circularNo + "==============");
        System.out.println("===========" + status + "==============");

        String query = "update  ris_new_job_posting_track set status = '" + status + "' where RIS_NEW_JOB_POSTING_ID = (select id from RIS_NEW_JOB_POSTING where RIS_NEW_JOB_POSTING.POSITION='" + position + "' and RIS_NEW_JOB_POSTING.DEPARTMENT='" + department + "' and RIS_NEW_JOB_POSTING.CIRCULARNO='" + circularNo + "')";

        System.out.println("\nquery for updating job request status " + query);
        int update = this.jdbcTemplate.update(query);
        System.out.println("jobRequestUpdate :" + update);
    }


    //jobrequestupdatebycirculano minister 2 , ar initial 1 denied by minister 9
    public void jobRequestUpdateByCircularNo(String cricularno, Integer status) {
        List<Map<String, Object>> ids = null;

        System.out.println("===========" + cricularno + "==============");
        System.out.println("===========" + status + "==============");

        /*String q = "update ris_new_job_posting_track set status="+status+" where id in (select id from ris_new_job_posting where circularno = '"+cricularno+"')";*/
        String qw = "select id from ris_new_job_posting where circularno = '" + cricularno + "'";
        System.out.println("\nThe Query for udpating job request by id :" + qw);
        ids = this.jdbcTemplate.queryForList(qw);
        List<Map<String, Object>> maplist;
        Map map = new HashMap<String, Integer>();

        System.out.println(map.get("id"));
        StringBuilder sb = new StringBuilder();
        sb.append("");
        System.out.println("jobRequestUpdate :" + ids.toString());

        for (Map<String, Object> maps : ids) {
            for (Map.Entry<String, Object> entry : maps.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                System.out.println(key);
                System.out.println(value.toString());
                sb.append(value.toString());
                sb.append(",");
            }
        }
        System.out.println(sb.toString().substring(0, sb.toString().length() - 1));
        String q = "update ris_new_job_posting_track set status=" + status + " where RIS_NEW_JOB_POSTING_ID in (" + sb.toString().substring(0, sb.toString().length() - 1) + ")";
        System.out.println("Query was:" + q);
        int update = this.jdbcTemplate.update(q);
        System.out.println("jobRequestUpdateByCircularNo :" + update);
        /*for(int i =0;i<ids.size();i++)
        {
            System.out.println("id"+ids.get(i));

        }
*/


    }

    //getting job by its status
    public List<Map<String, Object>> getjobRequest(Long status) {
        List<Map<String, Object>> list = null;
        System.out.println("===============" + status + "========================");
        System.out.println(status);

        list = this.jdbcTemplate.queryForList("SELECT * FROM ris_new_job_posting rnp, ris_new_job_posting_track rnt where rnp.id = rnt.id and rnt.status = ?", status);

        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }
    }


    public List<Map<String, Object>> findCustomColumnOfCmsSyllabuss () {

        List<Map<String, Object>> instituteList = this.jdbcTemplate.queryForList("select id, name from cms_syllabus");
        return instituteList;
    }



    public List<Map<String, Object>> getapplicantbycircularandstatus(String circularNo, Integer status) {
        List<Map<String, Object>> list = null;
        System.out.println("getapplicantbycircularandstatus" + status + "========================");
        System.out.println("getapplicantbycircularandstatus" + circularNo + "========================");
        System.out.println(status);

        list = this.jdbcTemplate.queryForList("select * from ris_new_app_form where application_status = " + status + " and circular_no = '" + circularNo + "'");

        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }
    }

    //getting getting all circular number
    public List<Map<String, Object>> getCircularNumber() {
        List<Map<String, Object>> list = null;
        System.out.println("===============Circular No RPT=======================");


        list = this.jdbcTemplate.queryForList("select distinct CIRCULARNO from ris_new_job_posting");

        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }
    }

    //getting getting all designation
    public List<Map<String, Object>> getDesignation() {
        List<Map<String, Object>> list = null;
        System.out.println("===============Circular No RPT=======================");


        list = this.jdbcTemplate.queryForList("select distinct DESIGNATION_NAME from HR_DESIGNATION_HEAD_SETUP order by DESIGNATION_NAME desc");

        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }
    }


    //getting applicants by their status
    public List<Map<String, Object>> getApplicantsByStatus(Long status) {
        List<Map<String, Object>> list = null;

        list = this.jdbcTemplate.queryForList("select * from ris_new_app_form where application_status =?", status);
       /* list = this.jdbcTemplate.queryForList("select * from RIS_NEW_EXAM_STATUS where STATUS =?", status);*/

        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }

    //getting only one applicant by their status
    public List<Map<String, Object>> getOneApplicantByStatus(String id, String status) {
        List<Map<String, Object>> list = null;

        list = this.jdbcTemplate.queryForList("select * from ris_new_app_form where application_status =? and id = ?", status, id);
       /* list = this.jdbcTemplate.queryForList("select * from RIS_NEW_EXAM_STATUS where STATUS =? and id = ?", status,id);*/

        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }

    }


    public List<Map<String, Object>> getApplicantsByStatusWithNumber(Long status, Long number) {
        List<Map<String, Object>> list = null;

        list = this.jdbcTemplate.queryForList("select * from ris_new_app_form where application_status =? and rownum <= ?", status, number);

       /* if (list != null && list.size() > 0) {
//            "where mpo.status < ? ",status);DESCRIPTION,UPDATE_DATE,UPLOAD,CONTENT_TYPE,UPLOADED_FILE_NAME*/
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }
    }
    //  ris ends




    //category wise job counter

    public List<Map<String, Object>> getCatsWiseJobCounter() {
        List<Map<String, Object>> list = null;

        String query = "SELECT a.id, a.cat, nvl(count(b.id),0) totalJob " +
            "FROM cat a left outer join job b " +
            "on a.id = b.cat_id  " +
            "and to_date(application_deadline,'DD/MM/RRRR') >= to_date(sysdate,'DD/MM/RRRR') " +
            "and a.cat is not null " +
            "and a.id is not null " +
            "GROUP BY a.id, a.cat";
        list = this.jdbcTemplate.queryForList(query);

       /* if (list != null && list.size() > 0) {
//            "where mpo.status < ? ",status);DESCRIPTION,UPDATE_DATE,UPLOAD,CONTENT_TYPE,UPLOADED_FILE_NAME*/
        if (list != null && list.size() > 0) {
            return list;
        } else {
            return new ArrayList<>();
        }
    }



//category wise job counter

    public List<CatJobDto> getCategoryWiseJobCounter() {

        String query = "SELECT a.id, a.cat, nvl(count(b.id),0) " +
            "FROM cat a left outer join job b " +
            "on a.id = b.cat_id  " +
            "and to_date(application_deadline,'DD/MM/RRRR') >= to_date(sysdate,'DD/MM/RRRR') " +
            "and a.cat is not null " +
            "and a.id is not null " +
            "GROUP BY a.id, a.cat";
        List<CatJobDto> catJobDtos  = this.jdbcTemplate.query(query,
            new BeanPropertyRowMapper<CatJobDto>(CatJobDto.class));
       return  catJobDtos;

    }



}
