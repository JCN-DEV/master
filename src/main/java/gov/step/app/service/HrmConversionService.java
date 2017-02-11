package gov.step.app.service;

import gov.step.app.domain.*;

import gov.step.app.service.constnt.HRMManagementConstant;
import gov.step.app.service.util.ObjectConversionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.inject.Inject;

/**
 * Created by Yousuf Zaman on 2/4/16.
 */
@Service
public class HrmConversionService
{
    private final Logger log = LoggerFactory.getLogger(HrmConversionService.class);

    @Inject
    private ObjectConversionUtil objectConverter;

    /**
     * Convert HrEmploymentInfoLog <-> HrEmploymentInfo
     * @param sourceInfo
     * @return
     */
    public HrEmploymentInfoLog getEmploymentLogFromSource(HrEmploymentInfo sourceInfo)
    {
        HrEmploymentInfoLog destInfo = new HrEmploymentInfoLog();
        log.debug("CONVERT Employment SOURCE to LOG Info");

        String[] restrictedFields = {"id","logStatus","createBy", "createDate"};
        destInfo  = (HrEmploymentInfoLog) objectConverter.convertClass(sourceInfo, destInfo, restrictedFields);

        destInfo.setId(null);
        destInfo.setParentId(sourceInfo.getId());
        destInfo.setCreateBy(sourceInfo.getUpdateBy());
        destInfo.setCreateDate(sourceInfo.getCreateDate());
        destInfo.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
        return destInfo;
    }

    public HrEmploymentInfo getEmploymentModelFromLog(HrEmploymentInfoLog logInfo, HrEmploymentInfo modelInfo)
    {
        log.debug("CONVERT Employment LOG to SOURCE Info");
        String[] restrictedFields = {"id", "logId","logStatus","activeStatus","createBy", "createDate","updateBy","updateDate"};
        modelInfo  = (HrEmploymentInfo) objectConverter.convertClass(logInfo, modelInfo, restrictedFields);

        return modelInfo;
    }

    /**
     * Convert HrEmpChildrenInfoLog <-> HrEmpChildrenInfo
     * @param sourceInfo
     * @return
     */
    public HrEmpChildrenInfoLog getChildrenLogFromSource(HrEmpChildrenInfo sourceInfo)
    {
        HrEmpChildrenInfoLog destInfo = new HrEmpChildrenInfoLog();
        log.debug("CONVERT Children SOURCE to LOG Info");
        String[] restrictedFields = {"id","logStatus","createBy", "createDate"};
        destInfo  = (HrEmpChildrenInfoLog) objectConverter.convertClass(sourceInfo, destInfo, restrictedFields);

        destInfo.setId(null);
        destInfo.setParentId(sourceInfo.getId());
        destInfo.setCreateBy(sourceInfo.getUpdateBy());
        destInfo.setCreateDate(sourceInfo.getCreateDate());
        destInfo.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
        return destInfo;
    }

    public HrEmpChildrenInfo getChildrenModelFromLog(HrEmpChildrenInfoLog logInfo, HrEmpChildrenInfo modelInfo)
    {
        log.debug("CONVERT Children LOG to SOURCE Info");
        String[] restrictedFields = {"id", "logId","logStatus","activeStatus","createBy", "createDate","updateBy","updateDate"};
        modelInfo  = (HrEmpChildrenInfo) objectConverter.convertClass(logInfo, modelInfo, restrictedFields);

        return modelInfo;
    }

    /**
     * Convert HrEmpAddressInfoLog <-> HrEmpAddressInfo
     * @param sourceInfo
     * @return
     */
    public HrEmpAddressInfoLog getAddressLogFromSource(HrEmpAddressInfo sourceInfo)
    {
        HrEmpAddressInfoLog destInfo = new HrEmpAddressInfoLog();
        log.debug("CONVERT Address SOURCE to LOG Info");
        String[] restrictedFields = {"id","logStatus","createBy", "createDate"};
        destInfo  = (HrEmpAddressInfoLog) objectConverter.convertClass(sourceInfo, destInfo, restrictedFields);

        destInfo.setId(null);
        destInfo.setParentId(sourceInfo.getId());
        destInfo.setCreateBy(sourceInfo.getUpdateBy());
        destInfo.setCreateDate(sourceInfo.getCreateDate());
        destInfo.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
        return destInfo;
    }

    public HrEmpAddressInfo getAddressModelFromLog(HrEmpAddressInfoLog logInfo, HrEmpAddressInfo modelInfo)
    {
        log.debug("CONVERT Address LOG to SOURCE Info");
        String[] restrictedFields = {"id", "logId","logStatus","activeStatus","createBy", "createDate","updateBy","updateDate"};
        modelInfo  = (HrEmpAddressInfo) objectConverter.convertClass(logInfo, modelInfo, restrictedFields);

        return modelInfo;
    }

    /**
     * Convert HrEmpProfMemberInfoLog <-> HrEmpProfMemberInfo
     * @param sourceInfo
     * @return
     */
    public HrEmpProfMemberInfoLog getProfMembrLogFromSource(HrEmpProfMemberInfo sourceInfo)
    {
        HrEmpProfMemberInfoLog destInfo = new HrEmpProfMemberInfoLog();
        log.debug("CONVERT Professional Mbr SOURCE to LOG Info");
        String[] restrictedFields = {"id","logStatus","createBy", "createDate"};
        destInfo  = (HrEmpProfMemberInfoLog) objectConverter.convertClass(sourceInfo, destInfo, restrictedFields);

        destInfo.setId(null);
        destInfo.setParentId(sourceInfo.getId());
        destInfo.setCreateBy(sourceInfo.getUpdateBy());
        destInfo.setCreateDate(sourceInfo.getCreateDate());
        destInfo.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
        return destInfo;
    }

    public HrEmpProfMemberInfo getProfMembrModelFromLog(HrEmpProfMemberInfoLog logInfo, HrEmpProfMemberInfo modelInfo)
    {
        log.debug("CONVERT Profession Mbr LOG to SOURCE Info");
        String[] restrictedFields = {"id", "logId","logStatus","activeStatus","createBy", "createDate","updateBy","updateDate"};
        modelInfo  = (HrEmpProfMemberInfo) objectConverter.convertClass(logInfo, modelInfo, restrictedFields);

        return modelInfo;
    }

    /**
     * Convert HrEmpAwardInfoLog <-> HrEmpAwardInfo
     * @param sourceInfo
     * @return
     */
    public HrEmpAwardInfoLog getAwardLogFromSource(HrEmpAwardInfo sourceInfo)
    {
        HrEmpAwardInfoLog destInfo = new HrEmpAwardInfoLog();
        log.debug("CONVERT Award SOURCE to LOG Info");
        String[] restrictedFields = {"id","logStatus","createBy", "createDate"};
        destInfo  = (HrEmpAwardInfoLog) objectConverter.convertClass(sourceInfo, destInfo, restrictedFields);

        destInfo.setId(null);
        destInfo.setParentId(sourceInfo.getId());
        destInfo.setCreateBy(sourceInfo.getUpdateBy());
        destInfo.setCreateDate(sourceInfo.getCreateDate());
        destInfo.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
        return destInfo;
    }

    public HrEmpAwardInfo getAwardModelFromLog(HrEmpAwardInfoLog logInfo, HrEmpAwardInfo modelInfo)
    {
        log.debug("CONVERT Award LOG to SOURCE Info");
        String[] restrictedFields = {"id", "logId","logStatus","activeStatus","createBy", "createDate","updateBy","updateDate"};
        modelInfo  = (HrEmpAwardInfo) objectConverter.convertClass(logInfo, modelInfo, restrictedFields);

        return modelInfo;
    }


    /**
     * Convert HrEmpForeignTourInfoLog <-> HrEmpForeignTourInfo
     * @param sourceInfo
     * @return
     */
    public HrEmpForeignTourInfoLog getForeignTourLogFromSource(HrEmpForeignTourInfo sourceInfo)
    {
        HrEmpForeignTourInfoLog destInfo = new HrEmpForeignTourInfoLog();
        log.debug("CONVERT ForeignTour SOURCE to LOG Info");
        String[] restrictedFields = {"id","logStatus","createBy", "createDate"};
        destInfo  = (HrEmpForeignTourInfoLog) objectConverter.convertClass(sourceInfo, destInfo, restrictedFields);

        destInfo.setId(null);
        destInfo.setParentId(sourceInfo.getId());
        destInfo.setCreateBy(sourceInfo.getUpdateBy());
        destInfo.setCreateDate(sourceInfo.getCreateDate());
        destInfo.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
        return destInfo;
    }

    public HrEmpForeignTourInfo getForeignTourModelFromLog(HrEmpForeignTourInfoLog logInfo, HrEmpForeignTourInfo modelInfo)
    {
        log.debug("CONVERT ForeignTour LOG to SOURCE Info");
        String[] restrictedFields = {"id", "logId","logStatus","activeStatus","createBy", "createDate","updateBy","updateDate"};
        modelInfo  = (HrEmpForeignTourInfo) objectConverter.convertClass(logInfo, modelInfo, restrictedFields);

        return modelInfo;
    }


    /**
     * Convert HrEmpOtherServiceInfoLog <-> HrEmpOtherServiceInfo
     * @param sourceInfo
     * @return
     */
    public HrEmpOtherServiceInfoLog getOtherServiceLogFromSource(HrEmpOtherServiceInfo sourceInfo)
    {
        HrEmpOtherServiceInfoLog destInfo = new HrEmpOtherServiceInfoLog();
        log.debug("CONVERT Other Service SOURCE to LOG Info");
        String[] restrictedFields = {"id","logStatus","createBy", "createDate"};
        destInfo  = (HrEmpOtherServiceInfoLog) objectConverter.convertClass(sourceInfo, destInfo, restrictedFields);

        destInfo.setId(null);
        destInfo.setParentId(sourceInfo.getId());
        destInfo.setCreateBy(sourceInfo.getUpdateBy());
        destInfo.setCreateDate(sourceInfo.getCreateDate());
        destInfo.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
        return destInfo;
    }

    public HrEmpOtherServiceInfo getOtherServiceModelFromLog(HrEmpOtherServiceInfoLog logInfo, HrEmpOtherServiceInfo modelInfo)
    {
        log.debug("CONVERT Other Service LOG to SOURCE Info");
        String[] restrictedFields = {"id", "logId","logStatus","activeStatus","createBy", "createDate","updateBy","updateDate"};
        modelInfo  = (HrEmpOtherServiceInfo) objectConverter.convertClass(logInfo, modelInfo, restrictedFields);

        return modelInfo;
    }


    /**
     * Convert HrEmpPublicationInfoLog <-> HrEmpPublicationInfo
     * @param sourceInfo
     * @return
     */
    public HrEmpPublicationInfoLog getPublicationLogFromSource(HrEmpPublicationInfo sourceInfo)
    {
        HrEmpPublicationInfoLog destInfo = new HrEmpPublicationInfoLog();
        log.debug("CONVERT Publication SOURCE to LOG Info");
        String[] restrictedFields = {"id","logStatus","createBy", "createDate"};
        destInfo  = (HrEmpPublicationInfoLog) objectConverter.convertClass(sourceInfo, destInfo, restrictedFields);

        destInfo.setId(null);
        destInfo.setParentId(sourceInfo.getId());
        destInfo.setCreateBy(sourceInfo.getUpdateBy());
        destInfo.setCreateDate(sourceInfo.getCreateDate());
        destInfo.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
        return destInfo;
    }

    public HrEmpPublicationInfo getPublicationModelFromLog(HrEmpPublicationInfoLog logInfo, HrEmpPublicationInfo modelInfo)
    {
        log.debug("CONVERT Publication LOG to SOURCE Info");
        String[] restrictedFields = {"id", "logId","logStatus","activeStatus","createBy", "createDate","updateBy","updateDate"};
        modelInfo  = (HrEmpPublicationInfo) objectConverter.convertClass(logInfo, modelInfo, restrictedFields);

        return modelInfo;
    }


    /**
     * Convert HrEmpTrainingInfoLog <-> HrEmpTrainingInfo
     * @param sourceInfo
     * @return
     */
    public HrEmpTrainingInfoLog getTrainingLogFromSource(HrEmpTrainingInfo sourceInfo)
    {
        HrEmpTrainingInfoLog destInfo = new HrEmpTrainingInfoLog();
        log.debug("CONVERT Training SOURCE to LOG Info");
        String[] restrictedFields = {"id","logStatus","createBy", "createDate"};
        destInfo  = (HrEmpTrainingInfoLog) objectConverter.convertClass(sourceInfo, destInfo, restrictedFields);

        destInfo.setId(null);
        destInfo.setParentId(sourceInfo.getId());
        destInfo.setCreateBy(sourceInfo.getUpdateBy());
        destInfo.setCreateDate(sourceInfo.getCreateDate());
        destInfo.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
        return destInfo;
    }

    public HrEmpTrainingInfo getTrainingModelFromLog(HrEmpTrainingInfoLog logInfo, HrEmpTrainingInfo modelInfo)
    {
        log.debug("CONVERT Training LOG to SOURCE Info");
        String[] restrictedFields = {"id", "logId","logStatus","activeStatus","createBy", "createDate","updateBy","updateDate"};
        modelInfo  = (HrEmpTrainingInfo) objectConverter.convertClass(logInfo, modelInfo, restrictedFields);

        return modelInfo;
    }

    /**
     * Convert HrEmpTransferInfoLog <-> HrEmpTransferInfo
     * @param sourceInfo
     * @return
     */
    public HrEmpTransferInfoLog getTransferLogFromSource(HrEmpTransferInfo sourceInfo)
    {
        HrEmpTransferInfoLog destInfo = new HrEmpTransferInfoLog();
        log.debug("CONVERT Transfer SOURCE to LOG Info");
        String[] restrictedFields = {"id","logStatus","createBy", "createDate"};
        destInfo  = (HrEmpTransferInfoLog) objectConverter.convertClass(sourceInfo, destInfo, restrictedFields);

        destInfo.setId(null);
        destInfo.setParentId(sourceInfo.getId());
        destInfo.setCreateBy(sourceInfo.getUpdateBy());
        destInfo.setCreateDate(sourceInfo.getCreateDate());
        destInfo.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
        return destInfo;
    }

    public HrEmpTransferInfo getTransferModelFromLog(HrEmpTransferInfoLog logInfo, HrEmpTransferInfo modelInfo)
    {
        log.debug("CONVERT Transfer LOG to SOURCE Info");
        String[] restrictedFields = {"id", "logId","logStatus","activeStatus","createBy", "createDate","updateBy","updateDate"};
        modelInfo  = (HrEmpTransferInfo) objectConverter.convertClass(logInfo, modelInfo, restrictedFields);

        return modelInfo;
    }

    /**
     * Convert HrNomineeInfoLog <-> HrNomineeInfo
     * @param sourceInfo
     * @return
     */
    public HrNomineeInfoLog getNomineeLogFromSource(HrNomineeInfo sourceInfo)
    {
        HrNomineeInfoLog destInfo = new HrNomineeInfoLog();
        log.debug("CONVERT Nominee SOURCE to LOG Info");
        String[] restrictedFields = {"id","logStatus","createBy", "createDate"};
        destInfo  = (HrNomineeInfoLog) objectConverter.convertClass(sourceInfo, destInfo, restrictedFields);

        destInfo.setId(null);
        destInfo.setParentId(sourceInfo.getId());
        destInfo.setCreateBy(sourceInfo.getUpdateBy());
        destInfo.setCreateDate(sourceInfo.getCreateDate());
        destInfo.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
        return destInfo;
    }

    public HrNomineeInfo getNomineeModelFromLog(HrNomineeInfoLog logInfo, HrNomineeInfo modelInfo)
    {
        log.debug("CONVERT Nominee LOG to SOURCE Info");
        String[] restrictedFields = {"id", "logId","logStatus","activeStatus","createBy", "createDate","updateBy","updateDate"};
        modelInfo  = (HrNomineeInfo) objectConverter.convertClass(logInfo, modelInfo, restrictedFields);

        return modelInfo;
    }

    /**
     * Convert HrSpouseInfoLog <-> HrSpouseInfo
     * @param sourceInfo
     * @return
     */
    public HrSpouseInfoLog getSpouseLogFromSource(HrSpouseInfo sourceInfo)
    {
        HrSpouseInfoLog destInfo = new HrSpouseInfoLog();
        log.debug("CONVERT Spouse SOURCE to LOG Info");
        String[] restrictedFields = {"id","logStatus","createBy", "createDate"};
        destInfo  = (HrSpouseInfoLog) objectConverter.convertClass(sourceInfo, destInfo, restrictedFields);

        destInfo.setId(null);
        destInfo.setParentId(sourceInfo.getId());
        destInfo.setCreateBy(sourceInfo.getUpdateBy());
        destInfo.setCreateDate(sourceInfo.getCreateDate());
        destInfo.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
        return destInfo;
    }

    public HrSpouseInfo getSpouseModelFromLog(HrSpouseInfoLog logInfo, HrSpouseInfo modelInfo)
    {
        log.debug("CONVERT Spouse LOG to SOURCE Info");
        String[] restrictedFields = {"id", "logId","logStatus","activeStatus","createBy", "createDate","updateBy","updateDate"};
        modelInfo  = (HrSpouseInfo) objectConverter.convertClass(logInfo, modelInfo, restrictedFields);

        return modelInfo;
    }

    /**
     * Convert HrEducationInfoLog <-> HrEducationInfo
     * @param sourceInfo
     * @return
     */
    public HrEducationInfoLog getLogFromSource(HrEducationInfo sourceInfo)
    {
        HrEducationInfoLog destInfo = new HrEducationInfoLog();
        log.debug("CONVERT Educatioin SOURCE to LOG Info");
        String[] restrictedFields = {"id","logStatus","createBy", "createDate"};
        destInfo  = (HrEducationInfoLog) objectConverter.convertClass(sourceInfo, destInfo, restrictedFields);

        destInfo.setId(null);
        destInfo.setParentId(sourceInfo.getId());
        destInfo.setCreateBy(sourceInfo.getUpdateBy());
        destInfo.setCreateDate(sourceInfo.getCreateDate());
        destInfo.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
        return destInfo;
    }

    public HrEducationInfo getModelFromLog(HrEducationInfoLog logInfo, HrEducationInfo modelInfo)
    {
        log.debug("CONVERT Education LOG to SOURCE Info");
        String[] restrictedFields = {"id", "logId","logStatus","activeStatus","createBy", "createDate","updateBy","updateDate"};
        modelInfo  = (HrEducationInfo) objectConverter.convertClass(logInfo, modelInfo, restrictedFields);

        return modelInfo;
    }

    /**
     * Convert HrEmployeeInfoLog <-> HrEmployeeInfo
     * @param sourceInfo
     * @return
     */
    public HrEmployeeInfoLog getLogFromSource(HrEmployeeInfo sourceInfo)
    {
        HrEmployeeInfoLog destInfo = new HrEmployeeInfoLog();
        log.debug("CONVERT Employee SOURCE to LOG Info");
        String[] restrictedFields = {"id","logStatus","createBy", "createDate"};
        destInfo  = (HrEmployeeInfoLog) objectConverter.convertClass(sourceInfo, destInfo, restrictedFields);

        destInfo.setId(null);
        destInfo.setParentId(sourceInfo.getId());
        destInfo.setCreateBy(sourceInfo.getUpdateBy());
        destInfo.setCreateDate(sourceInfo.getCreateDate());
        destInfo.setActionBy(sourceInfo.getUpdateBy());
        destInfo.setActionDate(sourceInfo.getUpdateDate());
        destInfo.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
        return destInfo;
    }

    public HrEmployeeInfo getModelFromLog(HrEmployeeInfoLog logInfo, HrEmployeeInfo modelInfo)
    {
        log.debug("CONVERT Employee LOG to SOURCE Info");
        String[] restrictedFields = {"id", "logId","logStatus","activeStatus","createBy", "createDate","updateBy","updateDate","actionBy","actionDate"};
        modelInfo  = (HrEmployeeInfo) objectConverter.convertClass(logInfo, modelInfo, restrictedFields);

        return modelInfo;
    }

    /**
     * Convert HrEmpPreGovtJobInfoLog <-> HrEmpPreGovtJobInfo
     * @param sourceInfo
     * @return
     */
    public HrEmpPreGovtJobInfoLog getLogFromSource(HrEmpPreGovtJobInfo sourceInfo)
    {
        HrEmpPreGovtJobInfoLog destInfo = new HrEmpPreGovtJobInfoLog();
        log.debug("CONVERT PreGovtJob SOURCE to LOG Info");
        String[] restrictedFields = {"id","logStatus","createBy", "createDate"};
        destInfo  = (HrEmpPreGovtJobInfoLog) objectConverter.convertClass(sourceInfo, destInfo, restrictedFields);

        destInfo.setId(null);
        destInfo.setParentId(sourceInfo.getId());
        destInfo.setCreateBy(sourceInfo.getUpdateBy());
        destInfo.setCreateDate(sourceInfo.getCreateDate());
        destInfo.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
        return destInfo;
    }

    public HrEmpPreGovtJobInfo getModelFromLog(HrEmpPreGovtJobInfoLog logInfo, HrEmpPreGovtJobInfo modelInfo)
    {
        log.debug("CONVERT PreGovtJob LOG to SOURCE Info");
        String[] restrictedFields = {"id", "logId","logStatus","activeStatus","createBy", "createDate","updateBy","updateDate"};
        modelInfo  = (HrEmpPreGovtJobInfo) objectConverter.convertClass(logInfo, modelInfo, restrictedFields);

        return modelInfo;
    }

    /**
     * Convert HrEntertainmentBenefitLog <-> HrEntertainmentBenefit
     * @param sourceInfo
     * @return
     */
    public HrEntertainmentBenefitLog getLogFromSource(HrEntertainmentBenefit sourceInfo)
    {
        HrEntertainmentBenefitLog destInfo = new HrEntertainmentBenefitLog();
        log.debug("CONVERT EntertainmentBenefit SOURCE to LOG Info");
        String[] restrictedFields = {"id","logStatus","createBy", "createDate"};
        destInfo  = (HrEntertainmentBenefitLog) objectConverter.convertClass(sourceInfo, destInfo, restrictedFields);

        destInfo.setId(null);
        destInfo.setParentId(sourceInfo.getId());
        destInfo.setCreateBy(sourceInfo.getUpdateBy());
        destInfo.setCreateDate(sourceInfo.getCreateDate());
        destInfo.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
        return destInfo;
    }

    public HrEntertainmentBenefit getModelFromLog(HrEntertainmentBenefitLog logInfo, HrEntertainmentBenefit modelInfo)
    {
        log.debug("CONVERT EntertainmentBenefit LOG to SOURCE Info");
        String[] restrictedFields = {"id", "logId","logStatus","activeStatus","createBy", "createDate","updateBy","updateDate"};
        modelInfo  = (HrEntertainmentBenefit) objectConverter.convertClass(logInfo, modelInfo, restrictedFields);

        return modelInfo;
    }

    /**
     * Convert HrEmpActingDutyInfoLog <-> HrEmpActingDutyInfo
     * @param sourceInfo
     * @return
     */
    public HrEmpActingDutyInfoLog getLogFromSource(HrEmpActingDutyInfo sourceInfo)
    {
        HrEmpActingDutyInfoLog destInfo = new HrEmpActingDutyInfoLog();
        log.debug("CONVERT ActingDuty SOURCE to LOG Info");
        String[] restrictedFields = {"id","logStatus","createBy", "createDate"};
        destInfo  = (HrEmpActingDutyInfoLog) objectConverter.convertClass(sourceInfo, destInfo, restrictedFields);

        destInfo.setId(null);
        destInfo.setParentId(sourceInfo.getId());
        destInfo.setCreateBy(sourceInfo.getUpdateBy());
        destInfo.setCreateDate(sourceInfo.getCreateDate());
        destInfo.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
        return destInfo;
    }

    public HrEmpActingDutyInfo getModelFromLog(HrEmpActingDutyInfoLog logInfo, HrEmpActingDutyInfo modelInfo)
    {
        log.debug("CONVERT ActingDuty LOG to SOURCE Info");
        String[] restrictedFields = {"id", "logId","logStatus","activeStatus","createBy", "createDate","updateBy","updateDate"};
        modelInfo  = (HrEmpActingDutyInfo) objectConverter.convertClass(logInfo, modelInfo, restrictedFields);

        return modelInfo;
    }

    /**
     * Convert HrEmpPromotionInfoLog <-> HrEmpPromotionInfo
     * @param sourceInfo
     * @return
     */
    public HrEmpPromotionInfoLog getLogFromSource(HrEmpPromotionInfo sourceInfo)
    {
        HrEmpPromotionInfoLog destInfo = new HrEmpPromotionInfoLog();
        log.debug("CONVERT Promotion SOURCE to LOG Info");
        String[] restrictedFields = {"id","logStatus","createBy", "createDate"};
        destInfo  = (HrEmpPromotionInfoLog) objectConverter.convertClass(sourceInfo, destInfo, restrictedFields);

        destInfo.setId(null);
        destInfo.setParentId(sourceInfo.getId());
        destInfo.setCreateBy(sourceInfo.getUpdateBy());
        destInfo.setCreateDate(sourceInfo.getCreateDate());
        destInfo.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
        return destInfo;
    }

    public HrEmpPromotionInfo getModelFromLog(HrEmpPromotionInfoLog logInfo, HrEmpPromotionInfo modelInfo)
    {
        log.debug("CONVERT Promotion LOG to SOURCE Info");
        String[] restrictedFields = {"id", "logId","logStatus","activeStatus","createBy", "createDate","updateBy","updateDate"};
        modelInfo  = (HrEmpPromotionInfo) objectConverter.convertClass(logInfo, modelInfo, restrictedFields);

        return modelInfo;
    }

    /**
     * Convert HrEmpServiceHistoryLog <-> HrEmpServiceHistory
     * @param sourceInfo
     * @return
     */
    public HrEmpServiceHistoryLog getLogFromSource(HrEmpServiceHistory sourceInfo)
    {
        HrEmpServiceHistoryLog destInfo = new HrEmpServiceHistoryLog();
        log.debug("CONVERT ServiceHistory SOURCE to LOG Info");
        String[] restrictedFields = {"id","logStatus","createBy", "createDate"};
        destInfo  = (HrEmpServiceHistoryLog) objectConverter.convertClass(sourceInfo, destInfo, restrictedFields);

        destInfo.setId(null);
        destInfo.setParentId(sourceInfo.getId());
        destInfo.setCreateBy(sourceInfo.getUpdateBy());
        destInfo.setCreateDate(sourceInfo.getCreateDate());
        destInfo.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
        return destInfo;
    }

    public HrEmpServiceHistory getModelFromLog(HrEmpServiceHistoryLog logInfo, HrEmpServiceHistory modelInfo)
    {
        log.debug("CONVERT ServiceHistory LOG to SOURCE Info");
        String[] restrictedFields = {"id", "logId","logStatus","activeStatus","createBy", "createDate","updateBy","updateDate"};
        modelInfo  = (HrEmpServiceHistory) objectConverter.convertClass(logInfo, modelInfo, restrictedFields);

        return modelInfo;
    }

    /**
     * Convert HrEmpServiceHistoryLog <-> HrEmpServiceHistory
     * @param sourceInfo
     * @return
     */
    public HrEmpBankAccountInfoLog getLogFromSource(HrEmpBankAccountInfo sourceInfo)
    {
        HrEmpBankAccountInfoLog destInfo = new HrEmpBankAccountInfoLog();
        log.debug("CONVERT ServiceHistory SOURCE to LOG Info");
        String[] restrictedFields = {"id","logStatus","createBy", "createDate"};
        destInfo  = (HrEmpBankAccountInfoLog) objectConverter.convertClass(sourceInfo, destInfo, restrictedFields);

        destInfo.setId(null);
        destInfo.setParentId(sourceInfo.getId());
        destInfo.setCreateBy(sourceInfo.getUpdateBy());
        destInfo.setCreateDate(sourceInfo.getCreateDate());
        destInfo.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
        return destInfo;
    }

    public HrEmpBankAccountInfo getModelFromLog(HrEmpBankAccountInfoLog logInfo, HrEmpBankAccountInfo modelInfo)
    {
        log.debug("CONVERT ServiceHistory LOG to SOURCE Info");
        String[] restrictedFields = {"id", "logId","logStatus","activeStatus","createBy", "createDate","updateBy","updateDate"};
        modelInfo  = (HrEmpBankAccountInfo) objectConverter.convertClass(logInfo, modelInfo, restrictedFields);

        return modelInfo;
    }

    /**
     * Convert HrEmpAcrInfoLog <-> HrEmpAcrInfo
     * @param sourceInfo
     * @return
     */
    public HrEmpAcrInfoLog getLogFromSource(HrEmpAcrInfo sourceInfo)
    {
        HrEmpAcrInfoLog destInfo = new HrEmpAcrInfoLog();
        log.debug("CONVERT HrEmpAcrInfo SOURCE to LOG Info");
        String[] restrictedFields = {"id","logStatus","createBy", "createDate"};
        destInfo  = (HrEmpAcrInfoLog) objectConverter.convertClass(sourceInfo, destInfo, restrictedFields);

        destInfo.setId(null);
        destInfo.setParentId(sourceInfo.getId());
        destInfo.setCreateBy(sourceInfo.getUpdateBy());
        destInfo.setCreateDate(sourceInfo.getCreateDate());
        destInfo.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
        return destInfo;
    }

    public HrEmpAcrInfo getModelFromLog(HrEmpAcrInfoLog logInfo, HrEmpAcrInfo modelInfo)
    {
        log.debug("CONVERT HrEmpAcrInfo LOG to SOURCE Info");
        String[] restrictedFields = {"id", "logId","logStatus","activeStatus","createBy", "createDate","updateBy","updateDate"};
        modelInfo  = (HrEmpAcrInfo) objectConverter.convertClass(logInfo, modelInfo, restrictedFields);

        return modelInfo;
    }

    /**
     * Convert HrEmpTransferApplInfoLog <-> HrEmpTransferApplInfo
     * @param sourceInfo
     * @return
     */
    public HrEmpTransferApplInfoLog getLogFromSource(HrEmpTransferApplInfo sourceInfo)
    {
        HrEmpTransferApplInfoLog destInfo = new HrEmpTransferApplInfoLog();
        log.debug("CONVERT HrEmpTransferApplInfo SOURCE to LOG Info");
        String[] restrictedFields = {"id","logStatus","createBy", "createDate"};
        destInfo  = (HrEmpTransferApplInfoLog) objectConverter.convertClass(sourceInfo, destInfo, restrictedFields);

        destInfo.setId(null);
        destInfo.setParentId(sourceInfo.getId());
        destInfo.setCreateBy(sourceInfo.getUpdateBy());
        destInfo.setCreateDate(sourceInfo.getCreateDate());
        destInfo.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
        return destInfo;
    }

    public HrEmpTransferApplInfo getModelFromLog(HrEmpTransferApplInfoLog logInfo, HrEmpTransferApplInfo modelInfo)
    {
        log.debug("CONVERT HrEmpTransferApplInfo LOG to SOURCE Info");
        String[] restrictedFields = {"id", "logId","logStatus","activeStatus","createBy", "createDate","updateBy","updateDate"};
        modelInfo  = (HrEmpTransferApplInfo) objectConverter.convertClass(logInfo, modelInfo, restrictedFields);

        return modelInfo;
    }

}
