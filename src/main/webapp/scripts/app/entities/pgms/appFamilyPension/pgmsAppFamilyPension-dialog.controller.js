'use strict';

angular.module('stepApp').controller('PgmsAppFamilyPensionDialogController',
    ['$rootScope','$sce','$scope', '$stateParams', '$state', 'entity', 'PgmsAppFamilyPension', 'PgmsAppFamilyAttach', 'HrEmployeeInfo', 'PgmsFmailyPenEmpInfo', 'PgmsRetirmntAttachInfo', 'PgmsAppFamilyAttachByTypeAndPen', 'User', 'Principal', 'DateUtils', 'DataUtils',
        function($rootScope,$sce,$scope, $stateParams, $state, entity, PgmsAppFamilyPension,PgmsAppFamilyAttach,HrEmployeeInfo,PgmsFmailyPenEmpInfo, PgmsRetirmntAttachInfo, PgmsAppFamilyAttachByTypeAndPen, User, Principal, DateUtils, DataUtils) {

        $scope.pgmsAppFamilyPension = entity;
        $scope.hremployeeinfos = HrEmployeeInfo.query();
        $scope.pgmsretirmntattachinfos = PgmsRetirmntAttachInfo.query();
        $scope.pgmsAppFamilyAttachList = [];
        var appPenId;
        if($stateParams.id == null){
          appPenId = 0;
        }
        else {
          appPenId = $stateParams.id;
        }
        $scope.viewFamilyAppForm = true;
        $scope.viewAttachForm = false;
        $scope.users = User.query({filter: 'pgmsAppFamilyPension-is-null'});

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
            PgmsAppFamilyAttachByTypeAndPen.get({attacheType:'family',familyPensionId:appPenId},
                function(result) {
                    $scope.pgmsAppFamilyAttachList = result;
                    console.log("Len: "+$scope.pgmsAppFamilyAttachList.length);
                    //console.log(JSON.stringify("Pgms App Family Attach List:"+$scope.pgmsAppFamilyAttachList));
                });

        };
        $scope.loadAll();

        $scope.load = function(id) {
            PgmsAppFamilyPension.get({id : id}, function(result) {
                $scope.pgmsAppFamilyPension = result;
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

        $scope.employeeInfo = function(empId,empNid){
           if(empNid == null){

                $scope.pgmsAppFamilyPension.empNid = empId.nationalId;
                $scope.pgmsAppFamilyPension.empName = empId.fullName;
                $scope.pgmsAppFamilyPension.empDepartment = empId.departmentInfo.departmentInfo.departmentName;
                $scope.pgmsAppFamilyPension.empDesignation = empId.designationInfo.designationInfo.designationName;
                var dataJsn = {employeeId:empId};
                console.log("dataJsn:"+JSON.stringify(dataJsn));
           }
           else
           {
               if(!empId){
                  var dataJsn = {employeeId:null, nid:$scope.pgmsAppFamilyPension.empNid};
               }
               else{
                  var dataJsn = {employeeId:empId.employeeId, nid:$scope.pgmsAppFamilyPension.empNid};
               }
               PgmsFmailyPenEmpInfo.get(dataJsn, function(result) {

                  if(!empId){

                      $scope.pgmsAppFamilyPension.appEmpId = result.employeeId;
                      $scope.pgmsAppFamilyPension.empName = result.fullName;
                      $scope.pgmsAppFamilyPension.empDepartment = result.departmentInfo.departmentName;
                      $scope.pgmsAppFamilyPension.empDesignation = result.designationInfo.designationName;
                 }
                 else{

                      $scope.pgmsAppFamilyPension.empNid = result.nationalId;
                      $scope.pgmsAppFamilyPension.empName = result.fullName;
                      $scope.pgmsAppFamilyPension.empDepartment = result.departmentInfo.departmentName;
                      $scope.pgmsAppFamilyPension.empDesignation = result.designationInfo.designationName;
                 }

                  // $scope.pgmsElpc.bankAcc = result.accountNumber;
                  console.log("Employee Details: "+JSON.stringify(result));

               });

           }
        }

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:pgmsAppFamilyPensionUpdate', result);
            $scope.isSaving = false;
            console.log("Attach Info Details: "+JSON.stringify($scope.pgmsAppFamilyAttachList));
            angular.forEach($scope.pgmsAppFamilyAttachList,function(application)
            {
               application.appFamilyPenId = result.id;
               if (application.id != null) {
                      PgmsAppFamilyAttach.update(application);
               } else {

                    PgmsAppFamilyAttach.save(application);
               }
            });
            $state.go("pgmsAppFamilyPension");
        };

        /*var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:pgmsAppFamilyAttachUpdate', result);
            $scope.isSaving = false;
            $scope.pgmsAppFamilyAttachList[$scope.selectedIndex].id=result.id;
        };

        $scope.saveAttachment = function (modelInfo, index)
        {
            $scope.selectedIndex = index;
            modelInfo.appFamilyPenId = $scope.appFamilyPenId;
            if (modelInfo.id != null)
            {
                PgmsAppFamilyAttach.update(modelInfo, onSaveSuccess, onSaveError);
            }
            else
            {
                PgmsAppFamilyAttach.save(modelInfo, onSaveSuccess, onSaveError);
            }
        };*/

        var onSaveError = function (result) {
             $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.pgmsAppFamilyPension.updateBy = $scope.loggedInUser.id;
            $scope.pgmsAppFamilyPension.updateDate = DateUtils.convertLocaleDateToServer(new Date());
            if ($scope.pgmsAppFamilyPension.id != null) {
                PgmsAppFamilyPension.update($scope.pgmsAppFamilyPension, onSaveFinished, onSaveError);
                $rootScope.setWarningMessage('stepApp.pgmsAppFamilyPension.updated');
            } else {
                $scope.pgmsAppFamilyPension.createBy = $scope.loggedInUser.id;
                $scope.pgmsAppFamilyPension.createDate = DateUtils.convertLocaleDateToServer(new Date());
                PgmsAppFamilyPension.save($scope.pgmsAppFamilyPension, onSaveFinished, onSaveError);
                $rootScope.setSuccessMessage('stepApp.pgmsAppFamilyPension.created');
            }
        };

        $scope.showHideForm = function(viewAppStat, viewAttachSat)
        {
            $scope.viewFamilyAppForm = viewAppStat;
            $scope.viewAttachForm = viewAttachSat;
        };

        $scope.clear = function() {
            $state.dismiss('cancel');
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        $scope.setAttachment = function ($file, pgmsAppFamilyAttach) {
                if ($file) {
                    var fileReader = new FileReader();
                    fileReader.readAsDataURL($file);
                    fileReader.onload = function (e) {
                        var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                        $scope.$apply(function() {
                            pgmsAppFamilyAttach.attachment = base64Data;
                            pgmsAppFamilyAttach.attachmentContentType = $file.type;
                            pgmsAppFamilyAttach.attachDocName = $file.name;
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
            $scope.pgmsAppFamilyAttach=modelInfo;
            $('#deletePgmsAppFamilyAttachConfirmation').modal('show');
        };

        $scope.confirmDelete = function (modelInfo)
        {
            PgmsAppFamilyAttach.delete({id: modelInfo.id},
                function () {
                    $('#deletePgmsAppFamilyAttachConfirmation').modal('hide');
                    $scope.clear(modelInfo);
                });

        };

        $scope.clear = function (modelInfo) {
            modelInfo.attachment= null;
            modelInfo.attachmentContentType= null;
            modelInfo.attachDocName= null;
            modelInfo.id= null;
        };
}]);
