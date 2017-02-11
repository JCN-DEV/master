package gov.step.app.web.rest.dto;

import gov.step.app.domain.*;

import java.util.List;

/**
 * Created by rana on 1/5/16.
 */
public class InstInfraDto {

    private InstInfraInfo instInfraInfo;
    private List<InstLabInfo> instLabInfoList;
    private List<InstPlayGroundInfo> instPlayGroundInfoList;
    private List<InstShopInfo> instShopInfoList;

    public InstInfraInfo getInstInfraInfo() {
        return instInfraInfo;
    }

    public void setInstInfraInfo(InstInfraInfo instInfraInfo) {
        this.instInfraInfo = instInfraInfo;
    }

    public List<InstLabInfo> getInstLabInfoList() {
        return instLabInfoList;
    }

    public void setInstLabInfoList(List<InstLabInfo> instLabInfoList) {
        this.instLabInfoList = instLabInfoList;
    }

    public List<InstPlayGroundInfo> getInstPlayGroundInfoList() {
        return instPlayGroundInfoList;
    }

    public void setInstPlayGroundInfoList(List<InstPlayGroundInfo> instPlayGroundInfoList) {
        this.instPlayGroundInfoList = instPlayGroundInfoList;
    }

    public List<InstShopInfo> getInstShopInfoList() {
        return instShopInfoList;
    }

    public void setInstShopInfoList(List<InstShopInfo> instShopInfoList) {
        this.instShopInfoList = instShopInfoList;
    }
}
