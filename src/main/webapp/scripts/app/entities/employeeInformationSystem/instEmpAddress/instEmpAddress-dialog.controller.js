'use strict';

angular.module('stepApp').controller('InstEmpAddressDialogController',
    ['$scope','$rootScope', '$stateParams', 'entity', 'InstEmpAddress', 'Upazila', 'InstEmployee',
        function($scope,$rootScope, $stateParams, entity, InstEmpAddress, Upazila, InstEmployee) {

        $scope.instEmpAddress = entity;
        $scope.upazilas = Upazila.query();
        $scope.instemployees = InstEmployee.query();
        $scope.load = function(id) {
            InstEmpAddress.get({id : id}, function(result) {
                $scope.instEmpAddress = result;
            });
        };
        CurrentInstEmployee.get({},function(res){
            console.log(res.mpoAppStatus);
            if(res.mpoAppStatus > 1){
                console.log("Eligible to apply");
                $rootScope.setErrorMessage('You have applied for mpo.So you are not allowed to edit');
                $state.go('employeeInfo.personalInfo');
            }
        });
        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instEmpAddressUpdate', result);
            //$modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.instEmpAddress.id != null) {
                InstEmpAddress.update($scope.instEmpAddress, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.instEmpAddress.updated');
            } else {
                InstEmpAddress.save($scope.instEmpAddress, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.instEmpAddress.created');
            }
        };

        $scope.clear = function() {
            scope.InstEmpAddress = null;
        };
}]);
