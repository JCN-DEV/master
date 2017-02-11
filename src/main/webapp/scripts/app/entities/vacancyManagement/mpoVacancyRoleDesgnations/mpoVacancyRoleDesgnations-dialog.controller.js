'use strict';

angular.module('stepApp').controller('MpoVacancyRoleDesgnationsDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$q', 'entity', 'MpoVacancyRoleDesgnations', 'MpoVacancyRole', 'User', 'InstEmplDesignation', 'CmsSubject',
        function($scope, $stateParams, $modalInstance, $q, entity, MpoVacancyRoleDesgnations, MpoVacancyRole, User, InstEmplDesignation, CmsSubject) {

        $scope.mpoVacancyRoleDesgnations = entity;
        $scope.mpovacancyroles = MpoVacancyRole.query();
        $scope.users = User.query();
        $scope.instempldesignations = InstEmplDesignation.query();
        $scope.cmssubjects = CmsSubject.query();
        $scope.load = function(id) {
            MpoVacancyRoleDesgnations.get({id : id}, function(result) {
                $scope.mpoVacancyRoleDesgnations = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:mpoVacancyRoleDesgnationsUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.mpoVacancyRoleDesgnations.id != null) {
                MpoVacancyRoleDesgnations.update($scope.mpoVacancyRoleDesgnations, onSaveSuccess, onSaveError);
            } else {
                MpoVacancyRoleDesgnations.save($scope.mpoVacancyRoleDesgnations, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
