'use strict';

angular.module('stepApp').controller('DlSourceSetUpDialogController',
    ['$scope', '$rootScope', '$state', '$stateParams', 'entity', 'DlSourceSetUp',
        function($scope, $rootScope, $state, $stateParams, entity, DlSourceSetUp) {

        $scope.dlSourceSetUp = entity;
        $scope.load = function(id) {
            DlSourceSetUp.get({id : id}, function(result) {
                $scope.dlSourceSetUp = result;
            });
        };
        $scope.dlSourceSetUp.status = true;

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:dlSourceSetUpUpdate', result);

            $scope.isSaving = false;
             $state.go('libraryInfo.dlSourceSetUp',{},{reload:true});

        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.dlSourceSetUp.id != null) {
                DlSourceSetUp.update($scope.dlSourceSetUp, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.dlSourceSetUp.updated');
            } else {
                DlSourceSetUp.save($scope.dlSourceSetUp, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.dlSourceSetUp.created');
            }
        };

        $scope.clear = function() {

        };
}]);
