'use strict';

angular.module('stepApp').controller('TraineeInformationDialogController',
    ['$scope', '$state', '$stateParams', 'entity', 'TraineeInformation', 'TrainingHeadSetup', 'HrEmployeeInfo','HrEmployeeInfoByEmployeeId','Division','District',
        function($scope, $state, $stateParams, entity, TraineeInformation, TrainingHeadSetup, HrEmployeeInfo,HrEmployeeInfoByEmployeeId,Division,District) {

        $scope.traineeInformation = entity;
        $scope.trainingheadsetups = TrainingHeadSetup.query();
        $scope.hremployeeinfos = HrEmployeeInfo.query();
        $scope.divisions = Division.query();
        $scope.districts = District.query();
        $scope.traineeInfos = [];

        //$scope.getDistrictByDivision = function(){
        //    if($scope.traineeInformation.division != null){
        //        DistrictsByDivision.query({id:$scope.traineeInformation.division.id},function(result){
        //            $scope.districts = result;
        //        });
        //    }
        //};

        //$scope.getDistrictByDivision = function(){
        //    District.query(function(result){
        //        $scope.districts = result;
        //    });
        //};

        $scope.load = function(id) {
            TraineeInformation.get({id : id}, function(result) {
                $scope.traineeInformation = result;
            });
        };

        var onSaveFinished = function (result) {
            $state.go('trainingInfo.traineeInformation', null, { reload: true });
            $scope.$emit('stepApp:traineeInformationUpdate', result);
           //  $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.traineeInformation.id != null) {
                TraineeInformation.update($scope.traineeInformation, onSaveFinished);
            } else {
                angular.forEach($scope.traineeInfos,function(data){
                    TraineeInformation.save(data);
                });
                // TraineeInformation.save($scope.traineeInformation, onSaveFinished);
            }
            $state.go('trainingInfo.traineeInformation', null, { reload: true });
        };

        $scope.clear = function() {
           $state.go('trainingInfo.traineeInformation', null, { reload: true });
        };

        $scope.getHrEmployeeInfo = function(value,indexValue) {
            console.log(indexValue);
            HrEmployeeInfoByEmployeeId.get({id: value}, function (hrEmployeeData) {
                console.log('--- '+ hrEmployeeData.fullName);
                $scope.traineeInfos[indexValue].hrEmployeeInfo = hrEmployeeData;
                $scope.traineeInfos[indexValue].traineeName = hrEmployeeData.fullName;
                $scope.traineeInfos[indexValue].gender = hrEmployeeData.gender;
                $scope.traineeInfos[indexValue].organization = hrEmployeeData.organizationType;
               //  $scope.traineeInformation.contactNumber = hrEmployeeData.mobileNumber;
            });
        }

        $scope.AddMoreTraineeInfo = function(){

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
