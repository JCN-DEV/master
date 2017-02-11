package gov.step.app.web.rest.dto;

import gov.step.app.domain.Job;

/**
 * Created by galeb on 15/10/2016.
 */
public class CatJobDto {

    private Long id;

    private String cat;

    private int totalJob;

    public Long getCatId() {
        return id;
    }

    public void setCatId(Long catId) {
        this.id = catId;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public int getTotalJob() {
        return totalJob;
    }

    public void setTotalJob(int totalJob) {
        this.totalJob = totalJob;
    }
}
