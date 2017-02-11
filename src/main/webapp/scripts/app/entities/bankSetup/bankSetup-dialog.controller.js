'use strict';

angular.module('stepApp').controller('BankSetupDialogController',
    ['$scope', '$state', '$stateParams', 'entity', 'BankSetup', 'Upazila',
        function($scope, $state, $stateParams, entity, BankSetup, Upazila) {

        $scope.bankSetup = entity;
        $scope.upazilas = Upazila.query();
        $scope.load = function(id) {
            BankSetup.get({id : id}, function(result) {
                $scope.bankSetup = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:bankSetupUpdate', result);
            //$modalInstance.close(result);
            $scope.isSaving = false;
            $state.go('bankSetup',{},{reload:true});

        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.bankSetup.id != null) {
                BankSetup.update($scope.bankSetup, onSaveSuccess, onSaveError);
            } else {
                BankSetup.save($scope.bankSetup, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            //$modalInstance.dismiss('cancel');
            $scope.bankSetup=null;

        };
}]);
