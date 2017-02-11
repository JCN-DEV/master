'use strict';

angular.module('stepApp').controller('HrEmpIncrementInfoDialogController',
    ['$scope', '$rootScope', '$stateParams', '$state', '$q', 'entity', 'HrEmpIncrementInfo', 'HrEmployeeInfo', 'MiscTypeSetupByCategory', 'HrPayScaleSetup','User','Principal','DateUtils',
        function($scope,$rootScope, $stateParams, $state, $q, entity, HrEmpIncrementInfo, HrEmployeeInfo, MiscTypeSetupByCategory, HrPayScaleSetup, User, Principal, DateUtils) {

        $scope.hrEmpIncrementInfo = entity;
        $scope.employeeinfos = HrEmployeeInfo.query({filter: 'hrempincrementinfo-is-null'});
        $q.all([$scope.hrEmpIncrementInfo.$promise, $scope.employeeinfos.$promise]).then(function() {
            if (!$scope.hrEmpIncrementInfo.employeeInfo || !$scope.hrEmpIncrementInfo.employeeInfo.id) {
                return $q.reject();
            }
            return HrEmployeeInfo.get({id : $scope.hrEmpIncrementInfo.employeeInfo.id}).$promise;
        }).then(function(employeeInfo) {
            $scope.employeeinfos.push(employeeInfo);
        });

        $scope.misctypesetups = MiscTypeSetupByCategory.get({cat:'JobCategory',stat:'true'});
        $scope.hrpayscalesetups = HrPayScaleSetup.query();
        $scope.load = function(id) {
            HrEmpIncrementInfo.get({id : id}, function(result) {
                $scope.hrEmpIncrementInfo = result;
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

        $scope.calendar = {
            opened: {},
            dateFormat: 'dd-MM-yyyy',
            dateOptions: {},
            open: function ($event, which) {
                $event.preventDefault();
                $event.stopPropagation();
                $scope.calendar.opened[which] = true;
            }
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:hrEmpIncrementInfoUpdate', result);
            $scope.isSaving = false;
            $state.go('hrEmpIncrementInfo');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function ()
        {
            $scope.isSaving = true;
            $scope.hrEmpIncrementInfo.updateBy = $scope.loggedInUser.id;
            $scope.hrEmpIncrementInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());

            if ($scope.hrEmpIncrementInfo.id != null)
            {
                $scope.hrEmpIncrementInfo.logId = 0;
                $scope.hrEmpIncrementInfo.logStatus = 0;
                HrEmpIncrementInfo.update($scope.hrEmpIncrementInfo, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.hrEmpIncrementInfo.updated');
            }
            else
            {
                $scope.hrEmpIncrementInfo.logId = 0;
                $scope.hrEmpIncrementInfo.logStatus = 0;
                $scope.hrEmpIncrementInfo.createBy = $scope.loggedInUser.id;
                $scope.hrEmpIncrementInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
                HrEmpIncrementInfo.save($scope.hrEmpIncrementInfo, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.hrEmpIncrementInfo.created');
            }
        };

        $scope.clear = function() {
        };
}]);
