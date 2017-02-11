'use strict';

angular.module('stepApp').controller('InstEmpSpouseInfoDialogController',
    ['$scope','$rootScope', '$stateParams', '$q', 'InstEmpSpouseInfoForCurrent', 'InstEmpSpouseInfo', 'CurrentInstEmployee', 'InstEmployee', 'InstEmpAddress','entity','$state',
        function($scope,$rootScope, $stateParams, $q, InstEmpSpouseInfoForCurrent, InstEmpSpouseInfo, CurrentInstEmployee, InstEmployee, InstEmpAddress, entity, $state) {
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
        $scope.instEmpSpouseInfo = entity;
        $q.all([$scope.instEmpSpouseInfo.$promise]).then(function(){
           return $scope.instEmpSpouseInfo.$promise;
        });
        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instEmpSpouseInfoUpdate', result);
            $scope.isSaving = false;
            $state.go('employeeInfo.spouseInfo',{},{reload: true});
        };
        var onSaveError = function (result) {
            $scope.isSaving = false;
        };
        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.instEmpSpouseInfo.id != null) {
                InstEmpSpouseInfo.update($scope.instEmpSpouseInfo, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.instEmpSpouseInfo.updated');
            } else {
                InstEmpSpouseInfo.save($scope.instEmpSpouseInfo, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.instEmpSpouseInfo.created');
            }
        };
        $scope.valgender = function() {
            console.log($scope.instEmpSpouseInfo.gender);
        };
}]);
