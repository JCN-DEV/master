'use strict';

angular.module('stepApp').controller('PrlEmpPaymentStopInfoDialogController',
    ['$scope', '$rootScope', '$stateParams', '$state', 'entity', 'PrlEmpPaymentStopInfo', 'HrEmployeeInfo','User','Principal','DateUtils',
        function($scope, $rootScope, $stateParams, $state, entity, PrlEmpPaymentStopInfo, HrEmployeeInfo, User, Principal, DateUtils) {

        $scope.prlEmpPaymentStopInfo = entity;
        $scope.hremployeeinfos = HrEmployeeInfo.query();
        $scope.load = function(id) {
            PrlEmpPaymentStopInfo.get({id : id}, function(result) {
                $scope.prlEmpPaymentStopInfo = result;
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
            dateFormat: 'yyyy-MM-dd',
            dateOptions: {},
            open: function ($event, which) {
                $event.preventDefault();
                $event.stopPropagation();
                $scope.calendar.opened[which] = true;
            }
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:prlEmpPaymentStopInfoUpdate', result);
            $state.go('prlEmpPaymentStopInfo');
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function ()
        {
            //console.log(JSON.stringify($scope.prlEmpPaymentStopInfo));
            $scope.isSaving = true;
            $scope.prlEmpPaymentStopInfo.updateBy = $scope.loggedInUser.id;
            $scope.prlEmpPaymentStopInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());
            if ($scope.prlEmpPaymentStopInfo.id != null) {
                PrlEmpPaymentStopInfo.update($scope.prlEmpPaymentStopInfo, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.prlEmpPaymentStopInfo.updated');
            } else {
                $scope.prlEmpPaymentStopInfo.createBy = $scope.loggedInUser.id;
                $scope.prlEmpPaymentStopInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
                PrlEmpPaymentStopInfo.save($scope.prlEmpPaymentStopInfo, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.prlEmpPaymentStopInfo.created');
            }
        };

        $scope.clear = function() {

        };
}]);
