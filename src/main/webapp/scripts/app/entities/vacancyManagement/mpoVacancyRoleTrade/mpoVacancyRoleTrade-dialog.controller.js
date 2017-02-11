'use strict';

angular.module('stepApp').controller('MpoVacancyRoleTradeDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$q', 'entity', 'MpoVacancyRoleTrade', 'MpoVacancyRole', 'User', 'CmsTrade',
        function($scope, $stateParams, $modalInstance, $q, entity, MpoVacancyRoleTrade, MpoVacancyRole, User, CmsTrade) {

        $scope.mpoVacancyRoleTrade = entity;
        $scope.mpovacancyroles = MpoVacancyRole.query();
        $scope.users = User.query();
        $scope.cmstrades = CmsTrade.query();
        $scope.load = function(id) {
            MpoVacancyRoleTrade.get({id : id}, function(result) {
                $scope.mpoVacancyRoleTrade = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:mpoVacancyRoleTradeUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.mpoVacancyRoleTrade.id != null) {
                MpoVacancyRoleTrade.update($scope.mpoVacancyRoleTrade, onSaveSuccess, onSaveError);
            } else {
                MpoVacancyRoleTrade.save($scope.mpoVacancyRoleTrade, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
