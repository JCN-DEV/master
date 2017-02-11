package gov.step.app.web.rest.dto;

import gov.step.app.domain.InstEmployee;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.File;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 * Created by leads on 1/19/16.
 */
public class TimeScaleApplicationDto {

    private Long id;

    private Date date;

    private String indexNo;

    private Integer status;

    private LocalDate resulationDate;

    private Integer agendaNumber;

    private Boolean workingBreak;

    private LocalDate workingBreakStart;

    private LocalDate workingBreakEnd;

    private Boolean disciplinaryAction;

    private String disActionCaseNo;

    private String disActionFileName;

    private byte[] disActionFile;

    private InstEmployee instEmployee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(String indexNo) {
        this.indexNo = indexNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDate getResulationDate() {
        return resulationDate;
    }

    public void setResulationDate(LocalDate resulationDate) {
        this.resulationDate = resulationDate;
    }

    public Integer getAgendaNumber() {
        return agendaNumber;
    }

    public void setAgendaNumber(Integer agendaNumber) {
        this.agendaNumber = agendaNumber;
    }

    public Boolean getWorkingBreak() {
        return workingBreak;
    }

    public void setWorkingBreak(Boolean workingBreak) {
        this.workingBreak = workingBreak;
    }

    public LocalDate getWorkingBreakStart() {
        return workingBreakStart;
    }

    public void setWorkingBreakStart(LocalDate workingBreakStart) {
        this.workingBreakStart = workingBreakStart;
    }

    public LocalDate getWorkingBreakEnd() {
        return workingBreakEnd;
    }

    public void setWorkingBreakEnd(LocalDate workingBreakEnd) {
        this.workingBreakEnd = workingBreakEnd;
    }

    public Boolean getDisciplinaryAction() {
        return disciplinaryAction;
    }

    public void setDisciplinaryAction(Boolean disciplinaryAction) {
        this.disciplinaryAction = disciplinaryAction;
    }

    public String getDisActionCaseNo() {
        return disActionCaseNo;
    }

    public void setDisActionCaseNo(String disActionCaseNo) {
        this.disActionCaseNo = disActionCaseNo;
    }

    public byte[] getDisActionFile() {
        return disActionFile;
    }

    public void setDisActionFile(byte[] disActionFile) {
        this.disActionFile = disActionFile;
    }

    public String getDisActionFileName() {
        return disActionFileName;
    }

    public void setDisActionFileName(String disActionFileName) {
        this.disActionFileName = disActionFileName;
    }

    public InstEmployee getInstEmployee() {
        return instEmployee;
    }

    public void setInstEmployee(InstEmployee instEmployee) {
        this.instEmployee = instEmployee;
    }

    @Override
    public String toString() {
        return "TimeScaleApplicationDto{" +
            "id=" + id +
            ", date=" + date +
            ", indexNo='" + indexNo + '\'' +
            ", status=" + status +
            ", resulationDate=" + resulationDate +
            ", agendaNumber=" + agendaNumber +
            ", workingBreak=" + workingBreak +
            ", workingBreakStart=" + workingBreakStart +
            ", workingBreakEnd=" + workingBreakEnd +
            ", disciplinaryAction=" + disciplinaryAction +
            ", disActionCaseNo='" + disActionCaseNo + '\'' +
            ", disActionFileName='" + disActionFileName + '\'' +
            ", disActionFile=" + Arrays.toString(disActionFile) +
            ", instEmployee=" + instEmployee +
            '}';
    }
}
