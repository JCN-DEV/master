'use strict';

angular.module('stepApp').controller('HrEmpGovtDuesInfoDialogController',
    ['$scope', '$rootScope', '$stateParams', '$state', 'entity', 'HrEmpGovtDuesInfo', 'HrEmployeeInfo','User','Principal','DateUtils',
        function($scope, $rootScope, $stateParams, $state, entity, HrEmpGovtDuesInfo, HrEmployeeInfo, User, Principal, DateUtils) {

        $scope.hrEmpGovtDuesInfo = entity;
        $scope.hremployeeinfos = HrEmployeeInfo.query();
        $scope.load = function(id) {
            HrEmpGovtDuesInfo.get({id : id}, function(result) {
                $scope.hrEmpGovtDuesInfo = result;
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
            $scope.$emit('stepApp:hrEmpGovtDuesInfoUpdate', result);
            $scope.isSaving = false;
            $state.go('hrEmpGovtDuesInfo');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function ()
        {
            $scope.isSaving = true;
            $scope.hrEmpGovtDuesInfo.updateBy = $scope.loggedInUser.id;
            $scope.hrEmpGovtDuesInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());

            if ($scope.hrEmpGovtDuesInfo.id != null)
            {
                $scope.hrEmpGovtDuesInfo.logId = 0;
                $scope.hrEmpGovtDuesInfo.logStatus = 0;
                HrEmpGovtDuesInfo.update($scope.hrEmpGovtDuesInfo, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.hrEmpGovtDuesInfo.updated');
            }
            else
            {
                $scope.hrEmpGovtDuesInfo.logId = 0;
                $scope.hrEmpGovtDuesInfo.logStatus = 0;
                $scope.hrEmpGovtDuesInfo.createBy = $scope.loggedInUser.id;
                $scope.hrEmpGovtDuesInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
                HrEmpGovtDuesInfo.save($scope.hrEmpGovtDuesInfo, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.hrEmpGovtDuesInfo.created');
            }
        };

        $scope.clear = function() {

        };
}]);
