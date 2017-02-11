'use strict';

angular.module('stepApp').controller('InstEmplBankInfoDialogController',
    ['$scope','$rootScope', '$stateParams', '$modalInstance', 'entity', 'InstEmplBankInfo', 'InstEmployee',
        function($scope,$rootScope, $stateParams, $modalInstance, entity, InstEmplBankInfo, InstEmployee) {

        $scope.instEmplBankInfo = entity;
        $scope.instemployees = InstEmployee.query();
        CurrentInstEmployee.get({},function(res){
            console.log(res.mpoAppStatus);
            if(res.mpoAppStatus > 1){
                console.log("Eligible to apply");
                $rootScope.setErrorMessage('You have applied for mpo.So you are not allowed to edit');
                $state.go('employeeInfo.personalInfo');
            }
        });
        $scope.load = function(id) {
            InstEmplBankInfo.get({id : id}, function(result) {
                $scope.instEmplBankInfo = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instEmplBankInfoUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.instEmplBankInfo.id != null) {
                InstEmplBankInfo.update($scope.instEmplBankInfo, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.instEmplBankInfo.updated');
            } else {
                InstEmplBankInfo.save($scope.instEmplBankInfo, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.instEmplBankInfo.created');
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
