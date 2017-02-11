package gov.step.app.web.rest.jdbc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/**
 * Created by rana on 2/10/16.
 */
public class InsttituteJdbcDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public InsttituteJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String,Object>> findInstgenInfoByStatus(Integer status1,Integer status2){
        return jdbcTemplate.queryForList(" select code,name,submited_date,CASE WHEN status is null THEN 0 ELSE status END AS status " +
            "from inst_gen_info where status=? or status=? order by submited_date ",status1,status2);
    }
    public List<Map<String,Object>> findInstgenInfopendingList(Integer status1){
        return jdbcTemplate.queryForList(" select code,name,submited_date,CASE WHEN status is null THEN 0 ELSE status END AS status " +
            "from inst_gen_info where status is null or status =0 or status>? order by submited_date ",status1);
    }


}
