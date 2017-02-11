'use strict';

angular.module('stepApp').controller('JpSkillLevelDialogController',
    ['$scope','$rootScope', '$stateParams', '$state', 'entity', 'JpSkillLevel',
        function($scope, $rootScope, $stateParams, $state, entity, JpSkillLevel) {

        $scope.jpSkillLevel = entity;
        $scope.load = function(id) {
            JpSkillLevel.get({id : id}, function(result) {
                $scope.jpSkillLevel = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:jpSkillLevelUpdate', result);
            //$modalInstance.close(result);
            $scope.isSaving = false;
            $state.go('jpSkillLevel', null, { reload: true });
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.jpSkillLevel.id != null) {
                JpSkillLevel.update($scope.jpSkillLevel, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.jpSkillLevel.updated');
            } else {
                JpSkillLevel.save($scope.jpSkillLevel, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.jpSkillLevel.created');
            }
        };

        $scope.clear = function() {
            //$modalInstance.dismiss('cancel');
        };
}]);
