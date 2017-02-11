'use strict';

angular.module('stepApp').controller('PrlSalaryAllowDeducInfoDialogController',
    ['$scope', '$stateParams', '$state', 'entity', 'PrlSalaryAllowDeducInfo', 'PrlSalaryStructureInfo', 'PrlAllowDeductInfo','User','Principal','DateUtils',
        function($scope, $stateParams, $state, entity, PrlSalaryAllowDeducInfo, PrlSalaryStructureInfo, PrlAllowDeductInfo, User, Principal, DateUtils) {

        $scope.prlSalaryAllowDeducInfo = entity;
        $scope.prlsalarystructureinfos = PrlSalaryStructureInfo.query();
        $scope.prlallowdeductinfos = PrlAllowDeductInfo.query();
        $scope.load = function(id) {
            PrlSalaryAllowDeducInfo.get({id : id}, function(result) {
                $scope.prlSalaryAllowDeducInfo = result;
            });
        };

        $scope.loggedInUser =   {};
        $scope.getLoggedInUser = function ()
        {
            Principal.identity().then(function (account)
            {
                User.get({login: account.login}, function (result)
                {
                    $scope.loggedInUser = result;
                });
            });
        };
        $scope.getLoggedInUser();

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:prlSalaryAllowDeducInfoUpdate', result);
            $scope.isSaving = false;
            $state.go('prlSalaryAllowDeducInfo');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            $scope.prlSalaryAllowDeducInfo.updateBy = $scope.loggedInUser.id;
            $scope.prlSalaryAllowDeducInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());
            if ($scope.prlSalaryAllowDeducInfo.id != null) {
                PrlSalaryAllowDeducInfo.update($scope.prlSalaryAllowDeducInfo, onSaveSuccess, onSaveError);
            } else {
                $scope.prlSalaryAllowDeducInfo.createBy = $scope.loggedInUser.id;
                $scope.prlSalaryAllowDeducInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
                PrlSalaryAllowDeducInfo.save($scope.prlSalaryAllowDeducInfo, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
        };
}]);
