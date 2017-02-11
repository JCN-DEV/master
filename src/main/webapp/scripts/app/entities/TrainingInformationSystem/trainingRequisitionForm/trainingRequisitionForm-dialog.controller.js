'use strict';

angular.module('stepApp').controller('TrainingRequisitionFormDialogController',
                ['$scope','$rootScope' ,'$sce','$state', '$stateParams', 'entity', 'TrainingRequisitionForm', 'GetAllTrainingCategoryByStatus', 'TrainingHeadSetup', 'Country', 'HrEmployeeInfo',
                'HrEmployeeInfoByEmployeeId','TraineeInformation','TraineeListByRequisition','GetAllTrainingHeadByStatus','Principal','InstEmployeeByInstitute','InstituteByLogin',
        function($scope,$rootScope,$sce ,$state, $stateParams, entity,TrainingRequisitionForm, GetAllTrainingCategoryByStatus, TrainingHeadSetup, Country, HrEmployeeInfo,
                 HrEmployeeInfoByEmployeeId,TraineeInformation,TraineeListByRequisition,GetAllTrainingHeadByStatus,Principal,InstEmployeeByInstitute,InstituteByLogin) {

        $scope.trainingRequisitionForm = entity;
        $scope.countrys = Country.query();
        $scope.country = true;
        $scope.employeeName = '';
        $scope.instituteName = '';
        $scope.joiningDate = '';
        $scope.traineeInfos = [];
        $scope.hremployeeinfos = [];
        $scope.instEmployeeinfos = [];

        if(Principal.hasAnyAuthority(['ROLE_INSTITUTE'])){
            console.log('Institute Apply');
            $scope.applyTypeInstitute = true;
            InstituteByLogin.query({},function(result){
                InstEmployeeByInstitute.query({instituteID:result.id},function(res){
                    $scope.instEmployeeinfos = res;
                });
            });
        }else{
            $scope.applyTypeDTE = true;
            console.log('DTE Apply and Admin');
            $scope.hremployeeinfos = HrEmployeeInfo.query({size:500});
        }


        GetAllTrainingCategoryByStatus.query({status:true},function(result){
           $scope.trainingcategorysetups = result;
        });
        GetAllTrainingHeadByStatus.query({status:true},function(data){
            $scope.trainingheadsetups = data;
        });


        // For Edit the form
        if ( $stateParams.id !=null) {
            TrainingRequisitionForm.get({id: $stateParams.id}, function (result) {
                $scope.trainingRequisitionForm = result;
                TraineeListByRequisition.query({pTrainingReqId: result.id}, function (data) {
                    $scope.traineeInfos = data;
                });
            });
        }

        $scope.load = function(id) {
            TrainingRequisitionForm.get({id : id}, function(result) {
                $scope.trainingRequisitionForm = result;
            });
        };

        var onSaveFinished = function (result) {

            angular.forEach($scope.traineeInfos,function(data){
                data.trainingRequisitionForm = result;
                TraineeInformation.save(data);
            });
            $state.go('trainingInfo.trainingRequisitionForm', null, { reload: true });
        };

        var onUpdateFinished = function (result) {

            angular.forEach($scope.traineeInfos,function(data){
                data.trainingRequisitionForm = result;
                TraineeInformation.update(data);
            });
            $state.go('trainingInfo.trainingRequisitionForm', null, { reload: true });
        };



        //console.log($scope.trainingRequisitionForm.instituteId);

        $scope.save = function () {
            if ($scope.trainingRequisitionForm.id != null) {
                TrainingRequisitionForm.update($scope.trainingRequisitionForm, onUpdateFinished);
                $rootScope.setWarningMessage('stepApp.trainingRequisitionForm.updated');
            } else {
                if($scope.trainingRequisitionForm.trainingType==null){
                    $scope.trainingRequisitionForm.trainingType = "Local";
                }
                TrainingRequisitionForm.save($scope.trainingRequisitionForm, onSaveFinished);
                $rootScope.setSuccessMessage('stepApp.trainingRequisitionForm.created');
            }
        };

        $scope.clear = function() {
            $state.go('trainingInfo.trainingRequisitionForm', null, { reload: true });
        };

        $scope.abbreviate = function (text) {
            if (!angular.isString(text)) {
                return '';
            }
            if (text.length < 30) {
                return text;
            }
            return text ? (text.substring(0, 15) + '...' + text.slice(-10)) : '';
        };

        $scope.byteSize = function (base64String) {
            if (!angular.isString(base64String)) {
                return '';
            }
            function endsWith(suffix, str) {
                return str.indexOf(suffix, str.length - suffix.length) !== -1;
            }
            function paddingSize(base64String) {
                if (endsWith('==', base64String)) {
                    return 2;
                }
                if (endsWith('=', base64String)) {
                    return 1;
                }
                return 0;
            }
            function size(base64String) {
                return base64String.length / 4 * 3 - paddingSize(base64String);
            }
            function formatAsBytes(size) {
                return size.toString().replace(/\B(?=(\d{3})+(?!\d))/g, " ") + " bytes";
            }

            return formatAsBytes(size(base64String));
        };

        $scope.setFile = function ($file, trainingRequisitionForm)
        {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function()
                    {
                        trainingRequisitionForm.file = base64Data;
                        trainingRequisitionForm.fileContentType = $file.type;
                        if (trainingRequisitionForm.fileContentName == null)
                        {
                            trainingRequisitionForm.fileContentName = $file.name;
                        }
                    });
                };
            }
        };

        $scope.previewFile = function (modelInfo)
        {
            var blob = $rootScope.b64toBlob(modelInfo.file, modelInfo.fileContentType);
            $rootScope.viewerObject.contentUrl = $sce.trustAsResourceUrl((window.URL || window.webkitURL).createObjectURL(blob));
            $rootScope.viewerObject.contentType = modelInfo.fileContentType;
            $rootScope.viewerObject.pageTitle = "Training Requisition Document";
            $rootScope.showPreviewModal();
        };

        $scope.AddMoreEmployee = function(){

            $scope.traineeInfos.push(
                {
                    status: null,
                    id: null
                }
            );
        };

        $scope.traineeInfos.push(
            {
                status: null,
                id: null
            }
        );



}]);
