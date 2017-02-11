'use strict';

angular.module('stepApp').controller('EduLevelDialogController',
    ['$scope','$rootScope', '$stateParams', '$state', 'entity', 'EduLevel',
        function($scope, $rootScope, $stateParams, $state, entity, EduLevel) {

        $scope.eduLevel = entity;
        $scope.load = function(id) {
            EduLevel.get({id : id}, function(result) {
                $scope.eduLevel = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:eduLevelUpdate', result);
            //$modalInstance.close(result);
            $scope.isSaving = false;
            $state.go('eduLevel', null, { reload: true });
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.eduLevel.id != null) {
                EduLevel.update($scope.eduLevel, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.eduLevel.updated');
            } else {
               /* if($scope.eduLevel.status == null){
                    $scope.eduLevel.status = true;
                }*/
                EduLevel.save($scope.eduLevel, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.eduLevel.created');
            }
        };

        $scope.clear = function() {
            //$modalInstance.dismiss('cancel');
        };
}]);
