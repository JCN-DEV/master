'use strict';

angular.module('stepApp').controller('JpSkillDialogController',
    ['$scope', '$rootScope', '$stateParams', '$state', 'entity', 'JpSkill',
        function($scope, $rootScope, $stateParams, $state, entity, JpSkill) {

        $scope.jpSkill = entity;
        $scope.load = function(id) {
            JpSkill.get({id : id}, function(result) {
                $scope.jpSkill = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:jpSkillUpdate', result);
            //$modalInstance.close(result);
            $scope.isSaving = false;
            $state.go('jpSkill', null, { reload: true });
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.jpSkill.id != null) {
                JpSkill.update($scope.jpSkill, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.jpSkill.updated');
            } else {
                JpSkill.save($scope.jpSkill, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.jpSkill.created');
            }
        };

        $scope.clear = function() {
            //$modalInstance.dismiss('cancel');
        };
}]);
