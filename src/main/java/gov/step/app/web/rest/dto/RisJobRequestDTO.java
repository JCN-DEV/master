package gov.step.app.web.rest.dto;

/**
 * Created by shafiqur on 6/16/16.
 */
public class RisJobRequestDTO {


    private String position;
    private String department;
    private String allocated ;
    private String currentEmployee;
    private String availableVacancy;
    private String stataus;
    private String circularno;
    private String job_id;


    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getAllocated() {
        return allocated;
    }

    public void setAllocated(String allocated) {
        this.allocated = allocated;
    }

    public String getCurrentEmployee() {
        return currentEmployee;
    }

    public void setCurrentEmployee(String currentEmployee) {
        this.currentEmployee = currentEmployee;
    }

    public String getAvailableVacancy() {
        return availableVacancy;
    }

    public void setAvailableVacancy(String availableVacancy) {
        this.availableVacancy = availableVacancy;
    }

    public String getStataus() {
        return stataus;
    }

    public void setStataus(String stataus) {
        this.stataus = stataus;
    }

    public String getCircularno() {
        return circularno;
    }

    public void setCircularno(String circularno) {
        this.circularno = circularno;
    }


    @Override
    public String toString() {
        return "RisJobRequestDTO{" +
            "position='" + position + '\'' +
            ", department='" + department + '\'' +
            ", allocated='" + allocated + '\'' +
            ", currentEmployee='" + currentEmployee + '\'' +
            ", availableVacancy='" + availableVacancy + '\'' +
            ", stataus='" + stataus + '\'' +
            ",job_id='" + job_id + '\'' +
            ", circularno='" + circularno + '\'' +
            '}';
    }
}
