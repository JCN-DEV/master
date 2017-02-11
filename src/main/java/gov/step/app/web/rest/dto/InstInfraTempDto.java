package gov.step.app.web.rest.dto;

import gov.step.app.domain.*;

import java.util.List;

/**
 * Created by rana on 1/5/16.
 */
public class InstInfraTempDto {

    private InstInfraInfoTemp instInfraInfo;
    private List<InstLabInfoTemp> instLabInfoList;
    private List<InstPlayGroundInfoTemp> instPlayGroundInfoList;
    private List<InstShopInfoTemp> instShopInfoList;

    public InstInfraInfoTemp getInstInfraInfo() {
        return instInfraInfo;
    }

    public void setInstInfraInfo(InstInfraInfoTemp instInfraInfo) {
        this.instInfraInfo = instInfraInfo;
    }

    public List<InstLabInfoTemp> getInstLabInfoList() {
        return instLabInfoList;
    }

    public void setInstLabInfoList(List<InstLabInfoTemp> instLabInfoList) {
        this.instLabInfoList = instLabInfoList;
    }

    public List<InstPlayGroundInfoTemp> getInstPlayGroundInfoList() {
        return instPlayGroundInfoList;
    }

    public void setInstPlayGroundInfoList(List<InstPlayGroundInfoTemp> instPlayGroundInfoList) {
        this.instPlayGroundInfoList = instPlayGroundInfoList;
    }

    public List<InstShopInfoTemp> getInstShopInfoList() {
        return instShopInfoList;
    }

    public void setInstShopInfoList(List<InstShopInfoTemp> instShopInfoList) {
        this.instShopInfoList = instShopInfoList;
    }
}
