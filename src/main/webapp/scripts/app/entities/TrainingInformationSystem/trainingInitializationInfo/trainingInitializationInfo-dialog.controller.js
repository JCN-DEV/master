'use strict';

angular.module('stepApp').controller('TrainingInitializationInfoDialogController',
    ['$scope', '$state', '$rootScope', '$stateParams','entity', 'TrainingInitializationInfo', 'TrainingHeadSetup', 'TrainingRequisitionForm','TrainingReqDataByReqCode',
        'TrainerInformation','Division','District','DistrictsByDivision','HrEmployeeInfoByEmployeeId','HrEmployeeInfo','TraineeListByRequisition','TrainerListByInitializationId',
        function($scope, $state, $rootScope, $stateParams, entity, TrainingInitializationInfo, TrainingHeadSetup, TrainingRequisitionForm,TrainingReqDataByReqCode,
                 TrainerInformation,Division,District, DistrictsByDivision,HrEmployeeInfoByEmployeeId,HrEmployeeInfo,TraineeListByRequisition,TrainerListByInitializationId) {

        $scope.trainingInitializationInfo = entity;
        $scope.trainingheadsetups = TrainingHeadSetup.query();
        $scope.trainingrequisitionforms = TrainingRequisitionForm.query();
        $scope.divisions = Division.query();
        $scope.timeScheduleDiv = true;
        $scope.trainingName = '';
        $scope.applyDate = '';
        $scope.employeeName = '';
        $scope.instituteName = '';
        $scope.sessionYear = '';
        $scope.trainerInfos = [];
        $scope.trainerInformation = {};
        $scope.trainerInfoDiv = true;
        $scope.endDateError = false;
        $scope.endDateInvalid = true;
        $scope.hremployeeinfos = HrEmployeeInfo.query({size:500});

        $scope.getDistricts = function(){
            DistrictsByDivision.query({id:$scope.trainingInitializationInfo.division.id},function(result){
                $scope.districts = result;
            });
        };

        if( $stateParams.id != null){
            $scope.endDateInvalid = false;
            TrainingInitializationInfo.get({id : $stateParams.id}, function(result) {
                $scope.trainingInitializationInfo = result;
                $scope.trainingInitializationInfo.requisitionId = result.trainingRequisitionForm.requisitionCode;
                $scope.getTrainingRequisitionData();
            });
            TrainerListByInitializationId.query({pInitializationId: $stateParams.id}, function (data) {
                $scope.trainerInfos = data;
            });
        }

        var onSaveFinished = function (result) {
            angular.forEach($scope.trainerInfos, function(data){
                console.log(data);
                data.trainingInitializationInfo = result;
                if(data.hrEmployeeInfo != null){
                    TrainerInformation.save(data);
                }else{
                    data.hrEmployeeInfo = null;
                    TrainerInformation.save(data);
                }
            });
            $state.go('trainingInfo.trainingInitializationInfo', null, { reload: true });
            $scope.$emit('stepApp:trainingInitializationInfoUpdate', result);
        };
        var onUpdateFinished = function (result) {
            angular.forEach($scope.trainerInfos, function(data){
                console.log('Update Call For Trainer Update');
                console.log(data);
                data.trainingInitializationInfo = result;
                if(data.hrEmployeeInfo !=null){
                    TrainerInformation.update(data);
                }else{
                    data.hrEmployeeInfo = null;
                    TrainerInformation.update(data);
                }
            });
            $state.go('trainingInfo.trainingInitializationInfo', null, { reload: true });
            $scope.$emit('stepApp:trainingInitializationInfoUpdate', result);
        };

        $scope.save = function () {
            if ($scope.trainingInitializationInfo.id != null) {
                console.log($scope.trainingInitializationInfo);
                TrainingInitializationInfo.update($scope.trainingInitializationInfo, onUpdateFinished);
                $rootScope.setWarningMessage('stepApp.trainingInitializationInfo.updated');
            } else {
                if($scope.trainingInitializationInfo.trainingType == null){
                    $scope.trainingInitializationInfo.trainingType = 'DTE';
                }
                console.log($scope.trainingInitializationInfo);
                TrainingInitializationInfo.save($scope.trainingInitializationInfo, onSaveFinished);
                $rootScope.setSuccessMessage('stepApp.trainingInitializationInfo.created');
            }
        };

        $scope.clear = function() {

        };
        $scope.isPageShow = function(value) {
            $scope.timeScheduleDiv = false;
        };

       $scope.getTrainingRequisitionData = function(){

           TrainingReqDataByReqCode.get({trainingReqcode:$scope.trainingInitializationInfo.requisitionId},function(result){
               $scope.trainingInitializationInfo.trainingRequisitionForm = result;
               $scope.trainingName = result.trainingHeadSetup.headName;
               $scope.applyDate = result.applyDate;
               if(result.hrEmployeeInfo !=null){
                   $scope.instituteName = 'DTE';
                   $scope.employeeName = result.hrEmployeeInfo.fullName;
               }
               if(result.institute != null){
                   $scope.instituteName = result.institute.name;
               }
               $scope.sessionYear = result.session;
               TraineeListByRequisition.query({pTrainingReqId : result.id},function(res){
                   $scope.trainingInitializationInfo.numberOfTrainee = res.length;
               });
           });

       };


        $scope.AddMoreTrainerInfo = function(){

            $scope.trainerInfos.push(
                {
                    status: null,
                    trainerType:null,
                    id: null
                }
            );
        };

        $scope.trainerInfos.push(
            {
                status: null,
                trainerType:null,
                id: null
            }
        );

        $scope.durationCalculate = function(a,b){
            $scope.endDateError = false;
            $scope.trainingInitializationInfo.duration = '';
            var _MS_PER_DAY = 1000 * 60 * 60 * 24;
            var utc1 = Date.UTC(a.getFullYear(), a.getMonth(), a.getDate());
            var utc2 = Date.UTC(b.getFullYear(), b.getMonth(), b.getDate());
            if(Math.floor((utc2 - utc1) / _MS_PER_DAY) >= 0){
                $scope.trainingInitializationInfo.duration = Math.floor((utc2 - utc1) / _MS_PER_DAY)+1;
                $scope.endDateError = false;
                $scope.endDateInvalid = false;
            }else{
                $scope.endDateError = true;
                $scope.endDateInvalid = true;
            }
        }


    }]);
