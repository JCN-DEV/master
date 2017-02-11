'use strict';

angular.module('stepApp').controller('PgmsAppRetirmntPenDialogController',
    ['$rootScope','$sce','$scope', '$stateParams', '$state', 'entity', 'PgmsAppRetirmntPen','PgmsAppRetirmntAttach', 'HrEmployeeInfo', 'PgmsAppRetirmntNmine','RetirementNomineeInfosByPenId','HrNomineeInfosByEmpId','BankInfosByEmpId','PgmsAppRetirementAttachByTypeAndPen','User','Principal','DateUtils','DataUtils',
        function($rootScope, $sce, $scope, $stateParams, $state, entity, PgmsAppRetirmntPen,PgmsAppRetirmntAttach, HrEmployeeInfo, PgmsAppRetirmntNmine,RetirementNomineeInfosByPenId, HrNomineeInfosByEmpId,BankInfosByEmpId,PgmsAppRetirementAttachByTypeAndPen ,User, Principal, DateUtils, DataUtils) {

        $scope.pgmsAppRetirmntPen = entity;
        $scope.hremployeeinfos = HrEmployeeInfo.query();
        $scope.viewPensionAppForm = true;
        $scope.viewPensionNomineForm = false;
        $scope.viewPensionAttachForm = false;
        /* For nominee Information */
        $scope.pgmsHrNomineeList = [];
        $scope.pgmsHrEmpBankInfo = [];
        $scope.totalList = null;
        var hrEmpId = 5864;
        /* For Attachment Information */
        $scope.pgmsAppRetirementAttachList = [];
        var appPenId;
        if($stateParams.id == null){
          appPenId = 0;
        }
        else {
          appPenId = $stateParams.id;
        }

        $scope.users = User.query({filter: 'pgmsAppRetirmntPen-is-null'});
        $scope.loggedInUser =   {};
        $scope.getLoggedInUser = function ()
        {
            Principal.identity().then(function (account)
            {
                User.get({login: account.login}, function (result)
                {
                    $scope.loggedInUser = result;
                });
            });
        };
        $scope.getLoggedInUser();

        $scope.loadAll = function()
        {
           if($stateParams.id == null)
           {
             HrNomineeInfosByEmpId.get({empId:hrEmpId},
                function(result) {
                      angular.forEach(result,function(dtoInfo){
                          $scope.pgmsHrNomineeList.push(
                              {
                                  appRetirmntPenId:null,
                                  nomineeStatus:null,
                                  nomineeName:dtoInfo.nomineeName,
                                  gender:dtoInfo.gender,
                                  relation:dtoInfo.nomineeRelationship.typeName,
                                  dateOfBirth:dtoInfo.birthDate,
                                  presentAddress:dtoInfo.address,
                                  nid:dtoInfo.nationalId,
                                  occupation:dtoInfo.occupation,
                                  designation:dtoInfo.designation,
                                  maritalStatus:'Marrid',
                                  mobileNo:dtoInfo.mobileNumber,
                                  getPension:null,
                                  hrNomineeInfo:null,
                                  id:null
                              }
                          );

                      });
                      $scope.totalList = $scope.pgmsHrNomineeList.length;
                      console.log("Nominee Length :"+$scope.totalList);
                      console.log("Nominee List :"+JSON.stringify($scope.pgmsHrNomineeList));
                });
           }else{
                RetirementNomineeInfosByPenId.get({penId:$stateParams.id},function(result) {
                  $scope.pgmsHrNomineeList = result;
                  console.log("Retirement Nominee List :"+JSON.stringify($scope.pgmsHrNomineeList));

                });

           }

           PgmsAppRetirementAttachByTypeAndPen.get({attacheType:'retirement',retirementPenId:appPenId},
               function(result) {
                   $scope.pgmsAppRetirementAttachList = result;
                   console.log("Retirement Len: "+$scope.pgmsAppRetirementAttachList.length);
                   console.log("Pgms App Retirement Attach List:"+JSON.stringify($scope.pgmsAppRetirementAttachList));
           });

           BankInfosByEmpId.get({empId:hrEmpId},function(result) {
             $scope.pgmsHrEmpBankInfo = result;
             console.log("Emp Bank Info :"+JSON.stringify($scope.pgmsHrEmpBankInfo));

           });

        };
        $scope.loadAll();

        $scope.load = function(id) {
            PgmsAppRetirmntPen.get({id : id}, function(result) {
                $scope.pgmsAppRetirmntPen = result;
            });
        };

        $scope.calendar = {
            opened: {},
            dateFormat: 'yyyy-MM-dd',
            dateOptions: {},
            open: function ($event, which) {
                $event.preventDefault();
                $event.stopPropagation();
                $scope.calendar.opened[which] = true;
            }
        };
        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:pgmsAppRetirmntPenUpdate', result);
             $scope.isSaving = false;

             angular.forEach($scope.pgmsHrNomineeList,function(application)
             {
                if(application.appRetirmntPenId == null) { application.appRetirmntPenId = result.id }
                if (application.id != null) {
                       PgmsAppRetirmntNmine.update(application);
                } else {

                    if(application.nomineeStatus == true ){
                     PgmsAppRetirmntNmine.save(application);
                    }
                }
             });

             angular.forEach($scope.pgmsAppRetirementAttachList,function(appattach)
             {
               // appattach.appRetirmntPenId = result.id;
                if(appattach.appRetirmntPenId == null) { appattach.appRetirmntPenId = result.id }
                if (appattach.id != null) {
                       PgmsAppRetirmntAttach.update(appattach);
                } else {

                     PgmsAppRetirmntAttach.save(appattach);
                }
             });
             $state.go("pgmsAppRetirmntPen");
        };

        var onSaveError = function (result) {
             $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.pgmsAppRetirmntPen.updateBy = $scope.loggedInUser.id;
            $scope.pgmsAppRetirmntPen.updateDate = DateUtils.convertLocaleDateToServer(new Date());
            if ($scope.pgmsAppRetirmntPen.id != null) {
                PgmsAppRetirmntPen.update($scope.pgmsAppRetirmntPen, onSaveFinished, onSaveError);
                $rootScope.setWarningMessage('stepApp.pgmsAppRetirmntPen.updated');
            } else {
                $scope.pgmsAppRetirmntPen.createBy = $scope.loggedInUser.id;
                $scope.pgmsAppRetirmntPen.createDate = DateUtils.convertLocaleDateToServer(new Date());
                PgmsAppRetirmntPen.save($scope.pgmsAppRetirmntPen, onSaveFinished, onSaveError);
                $rootScope.setSuccessMessage('stepApp.pgmsAppRetirmntPen.created');
            }
        };
        $scope.calendar = {
            opened: {},
            dateFormat: 'yyyy-MM-dd',
            dateOptions: {},
            open: function ($event, which) {
                $event.preventDefault();
                $event.stopPropagation();
                $scope.calendar.opened[which] = true;
            }
        };
        $scope.addMore = function()
        {
            $scope.pgmsHrNomineeList.push(
                {
                   appRetirmntPenId: null,
                   nomineeStatus: true,
                   nomineeName: null,
                   gender: null,
                   relation: null,
                   dateOfBirth: null,
                   presentAddress: null,
                   nid: null,
                   occupation: null,
                   designation: null,
                   maritalStatus: null,
                   mobileNo: null,
                   getPension: null,
                   hrNomineeInfo: null,
                   id: null
                }
            );
            $scope.addmorelist = 'addView';
        };
        $scope.showHideForm = function(viewPenApp, viewNomineeInfo,viewAttachInfo)
        {
            $scope.viewPensionAppForm = viewPenApp;
            $scope.viewPensionNomineForm = viewNomineeInfo;
            $scope.viewPensionAttachForm = viewAttachInfo;
        };

        $scope.clear = function() {
            $state.dismiss('cancel');
        };

        $scope.abbreviate = DataUtils.abbreviate;
        $scope.byteSize = DataUtils.byteSize;

        $scope.setAttachment = function ($file, pgmsAppRetirmntAttach) {
                if ($file) {
                    var fileReader = new FileReader();
                    fileReader.readAsDataURL($file);
                    fileReader.onload = function (e) {
                        var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                        $scope.$apply(function() {
                            pgmsAppRetirmntAttach.attachment = base64Data;
                            pgmsAppRetirmntAttach.attachmentContentType = $file.type;
                            pgmsAppRetirmntAttach.attachDocName = $file.name;
                        });
                    };
                }
        };

        $scope.previewDoc = function (modelInfo)
        {
            var blob = $rootScope.b64toBlob(modelInfo.attachment, modelInfo.attachmentContentType);
            $rootScope.viewerObject.contentUrl = $sce.trustAsResourceUrl((window.URL || window.webkitURL).createObjectURL(blob));
            $rootScope.viewerObject.contentType = modelInfo.attachmentContentType;
            $rootScope.viewerObject.pageTitle = "Preview of Attachment Order Document";
            $rootScope.showPreviewModal();
        };

        $scope.delete = function (modelInfo) {
            $scope.pgmsAppRetirmntAttach = modelInfo;
            $('#deletePgmsAppRetirmntAttachConfirmation').modal('show');
        };

        $scope.confirmDelete = function (modelInfo)
        {
            PgmsAppRetirmntAttach.delete({id: modelInfo.id},
                function () {
                    $('#deletePgmsAppRetirmntAttachConfirmation').modal('hide');
                    $scope.clear(modelInfo);
                });

        };

        $scope.bankInfoView = function(infoValo)
        {
            if(infoValo == true)
            {
              $scope.pgmsAppRetirmntPen.bankName = $scope.pgmsHrEmpBankInfo.bankName.typeName;
              $scope.pgmsAppRetirmntPen.bankAcc = $scope.pgmsHrEmpBankInfo.accountNumber;
              $scope.pgmsAppRetirmntPen.bankBranch = $scope.pgmsHrEmpBankInfo.branchName;

            }
            else{
              $scope.pgmsAppRetirmntPen.bankName = null;
              $scope.pgmsAppRetirmntPen.bankAcc = null;
              $scope.pgmsAppRetirmntPen.bankBranch = null;
            }
        };

        $scope.WorkDurationView = function(workInfo)
        {
            if(workInfo == 'willful')
            {
              $scope.pgmsAppRetirmntPen.workDuration = null;
            }
        };

        $scope.clear = function (modelInfo) {
            modelInfo.attachment= null;
            modelInfo.attachmentContentType= null;
            modelInfo.attachDocName= null;
            modelInfo.id= null;
        };
}]);
