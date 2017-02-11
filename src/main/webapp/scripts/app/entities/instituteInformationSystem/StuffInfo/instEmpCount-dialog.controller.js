'use strict';

angular.module('stepApp').controller('InstEmpCountDialogController',
    ['$scope','$rootScope', '$stateParams', '$q','$state' ,'entity', 'InstEmpCount', 'Institute',
        function($scope,$rootScope, $stateParams, $q,$state, entity, InstEmpCount, Institute) {

        $scope.instEmpCount = entity;
        $scope.showMessage = false;
        $scope.flagData=$scope.instEmpCount.totalGranted;
        /*$scope.institutes = Institute.query({filter: 'instempcount-is-null'});
        $q.all([$scope.instEmpCount.$promise, $scope.institutes.$promise]).then(function() {
            if (!$scope.instEmpCount.institute.id) {
                return $q.reject();
            }
            return Institute.get({id : $scope.instEmpCount.institute.id}).$promise;
        }).then(function(institute) {
            $scope.institutes.push(institute);
        });*/
        $scope.load = function(id) {
            InstEmpCount.get({id : id}, function(result) {
                $scope.instEmpCount = result;
            });
        };

        $scope.totalGranted = function(data){
            if(data > $scope.instEmpCount.totalMaleTeacher + $scope.instEmpCount.totalFemaleTeacher + $scope.instEmpCount.totalMaleEmployee + $scope.instEmpCount.totalFemaleEmployee){
                $scope.instEmpCount.totalGranted = $scope.flagData;
                $scope.showMessage = true;
                console.log("check greater than");
            }
            else{
                $scope.flagData = data;
                $scope.showMessage = false;
                console.log("check less than");
            }

        }

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instEmpCountUpdate', result);
            //$modalInstance.close(result);
            $scope.isSaving = false;
            $state.go('instituteInfo.stuffInfo',{},{reload:true});
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
            $state.go('instGenInfo.instEmpCount');
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.instEmpCount.id != null) {
                if($scope.instEmpCount.status==1){
                    console.log('  after approved');
                    $scope.instEmpCount.status=3;
                $scope.instEmpCount.totalEngaged = $scope.instEmpCount.totalMaleTeacher+ $scope.instEmpCount.totalFemaleTeacher+ $scope.instEmpCount.totalMaleEmployee+ $scope.instEmpCount.totalFemaleEmployee;
                InstEmpCount.update($scope.instEmpCount, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.instEmpCount.updated');

                }else{
                    console.log('  before approved');
                    $scope.instEmpCount.status=0;
                    $scope.instEmpCount.totalEngaged = $scope.instEmpCount.totalMaleTeacher+ $scope.instEmpCount.totalFemaleTeacher+ $scope.instEmpCount.totalMaleEmployee+ $scope.instEmpCount.totalFemaleEmployee;
                    InstEmpCount.update($scope.instEmpCount, onSaveSuccess, onSaveError);
                    $rootScope.setSuccessMessage('stepApp.instEmpCount.updated');
                }
                /*$scope.instEmpCount.status=0;
                $scope.instEmpCount.totalEngaged = $scope.instEmpCount.totalMaleTeacher+ $scope.instEmpCount.totalFemaleTeacher+ $scope.instEmpCount.totalMaleEmployee+ $scope.instEmpCount.totalFemaleEmployee;
                InstEmpCount.update($scope.instEmpCount, onSaveSuccess, onSaveError);*/
            } else {
                $scope.instEmpCount.totalEngaged = $scope.instEmpCount.totalMaleTeacher+ $scope.instEmpCount.totalFemaleTeacher+ $scope.instEmpCount.totalMaleEmployee+ $scope.instEmpCount.totalFemaleEmployee;
                console.log($scope.instEmpCount.institute);
                InstEmpCount.save($scope.instEmpCount, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.instEmpCount.created');
            }
        };

        $scope.clear = function() {
        $scope.instEmpCount = null;
        };
}]);
