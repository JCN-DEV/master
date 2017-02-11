package gov.step.app.web.rest.jdbc.dao;

import gov.step.app.domain.TraineeInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by bappimazumder on 10/24/16.
 */
@Component
public class TISJdbcDao {


    private final Logger logger = LoggerFactory.getLogger(TISJdbcDao.class);

    private final JdbcTemplate jdbcTemplate;

    private  final String SQL_TRAINEE_INFO_BY_EMPLOYEEID_AND_TRAININGCODE = "SELECT tti.* FROM TIS_TRAINEE_INFORMATION tti INNER JOIN TIS_INITIALIZATION_INFO tii \n" +
                                                                             " ON (tti.TRAINING_REQUISITION_ID = tii.TRAININGREQUISITIONFORM_ID)\n" +
                                                                            "WHERE tti.EMPLOYEE_INFO_ID = ? and tii.TRAINING_CODE = ? and tti.STATUS = 1";

    @Autowired
    public TISJdbcDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }



    // Get Trainee Information by employeeInfoId and TrainingCode
    public TraineeInformation getTraineeInfoByEmployeeIdAndTrainingCode(Long employeeInfoId, String trainingCode){
        logger.info(" Execute getTraineeInfoByEmployeeIdAndTrainingCode -- ");
        TraineeInformation traineeInformation = null;
        try {
            traineeInformation = (TraineeInformation) jdbcTemplate.queryForObject(SQL_TRAINEE_INFO_BY_EMPLOYEEID_AND_TRAININGCODE,new Object[]{employeeInfoId,trainingCode},new BeanPropertyRowMapper(TraineeInformation.class));
        }catch (Exception ex){
            logger.error("getTraineeInfoByEmployeeIdAndTrainingCode Msg: "+ex.getMessage());
        }
        return  traineeInformation;
    }


}
