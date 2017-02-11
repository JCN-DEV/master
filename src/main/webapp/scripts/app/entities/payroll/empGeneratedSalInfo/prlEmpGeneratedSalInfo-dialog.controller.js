'use strict';

angular.module('stepApp').controller('PrlEmpGeneratedSalInfoDialogController',
    ['$scope', '$rootScope', '$stateParams', '$state', 'entity', 'PrlEmpGeneratedSalInfo', 'PrlGeneratedSalaryInfo', 'PrlSalaryStructureInfo', 'HrEmployeeInfo','User','Principal','DateUtils',
        function($scope, $rootScope, $stateParams, $state, entity, PrlEmpGeneratedSalInfo, PrlGeneratedSalaryInfo, PrlSalaryStructureInfo, HrEmployeeInfo, User, Principal, DateUtils) {

        $scope.prlEmpGeneratedSalInfo = entity;
        $scope.prlgeneratedsalaryinfos = PrlGeneratedSalaryInfo.query();
        $scope.prlsalarystructureinfos = PrlSalaryStructureInfo.query();
        $scope.hremployeeinfos = HrEmployeeInfo.query();
        $scope.load = function(id) {
            PrlEmpGeneratedSalInfo.get({id : id}, function(result) {
                $scope.prlEmpGeneratedSalInfo = result;
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
            $scope.$emit('stepApp:prlEmpGeneratedSalInfoUpdate', result);
            $scope.isSaving = false;
            $state.go('prlEmpGeneratedSalInfo');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            $scope.prlEmpGeneratedSalInfo.updateBy = $scope.loggedInUser.id;
            $scope.prlEmpGeneratedSalInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());
            if ($scope.prlEmpGeneratedSalInfo.id != null) {
                PrlEmpGeneratedSalInfo.update($scope.prlEmpGeneratedSalInfo, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.prlEmpGeneratedSalInfo.updated');
            } else {
                $scope.prlEmpGeneratedSalInfo.createBy = $scope.loggedInUser.id;
                $scope.prlEmpGeneratedSalInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
                PrlEmpGeneratedSalInfo.save($scope.prlEmpGeneratedSalInfo, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.prlEmpGeneratedSalInfo.created');
            }
        };

        $scope.clear = function() {

        };
}]);
