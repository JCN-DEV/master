'use strict';

angular.module('stepApp')
    .controller('ProfessorDenyDialogController',
    ['$scope','MpoApplication','TimeScaleApplication','TimeScaleApplicationStatusLog', '$stateParams', 'DateUtils', '$state', '$modalInstance', 'MpoApplicationStatusLog',
    function ($scope,MpoApplication,TimeScaleApplication,TimeScaleApplicationStatusLog, $stateParams, DateUtils, $state, $modalInstance, MpoApplicationStatusLog) {
        /*$scope.timescaleApplication = entity;*/
        console.log($stateParams.apAll);
        $scope.timescaleApplication = $stateParams.apAll;
        $scope.timescaleApplicationStatusLog = {};

        console.log($scope.timescaleApplication);
        $scope.confirmDecline = function(){
            if($scope.timescaleApplication.id > 0 && $scope.causeDeny){
                $scope.timescaleApplicationStatusLog.status = 1;
                $scope.timescaleApplicationStatusLog.timeScaleApplicationId = $scope.timescaleApplication;
                $scope.timescaleApplicationStatusLog.cause=$scope.causeDeny;
                $scope.timescaleApplicationStatusLog.fromDate= DateUtils.convertLocaleDateToServer(new Date());
                TimeScaleApplicationStatusLog.save($scope.timescaleApplicationStatusLog, onSaveSuccess, onSaveError);
                console.log("cause found");
            }
            console.log($scope.timescaleApplication.id);
            console.log($scope.causeDeny);
            //MpoApplication.update($scope.mpoApplication, onSaveSuccess, onSaveError);
        }
        var onSaveSuccess = function(result){
            console.log("Timescale Applicatrion decline successfull");
            console.log(result);
            $modalInstance.close();
        }
        var onSaveError = function(result){
                   console.log(result);
        }
        $scope.clear = function(){
            $modalInstance.close();
        }



        $scope.submitForm = function(){
            console.log(">>>>>>>>>>>");
            console.log($scope.causeDeny);
            //$modalInstance.close();
        }

    }]);
