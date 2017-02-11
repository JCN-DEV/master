'use strict';

angular.module('stepApp').controller('SmsServiceAssignDialogController',
    ['$scope', '$state', '$stateParams', 'entity', 'SmsServiceAssign', 'SmsServiceDepartment', 'Employee', 'User', 'Principal',
        function($scope, $state, $stateParams, entity, SmsServiceAssign, SmsServiceDepartment, Employee, User, Principal)
    {

        $scope.smsServiceAssign = entity;
        $scope.smsservicedepartments = SmsServiceDepartment.query();
        $scope.employees = Employee.query();
        $scope.users = User.query();
        $scope.load = function(id) {
            SmsServiceAssign.get({id : id}, function(result) {
                $scope.smsServiceAssign = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:smsServiceAssignUpdate', result);
            $scope.isSaving = false;
            $state.go('smsServiceAssign');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {


            if($scope.smsServiceAssign.smsServiceDepartment==null)
            {
                $scope.responseMessage = "Please select service department!!!";
            }
            else if($scope.smsServiceAssign.employee==null)
            {
                $scope.responseMessage = "Please select employee!!!";
            }
            else
            {
                $scope.isSaving = true;
                if ($scope.smsServiceAssign.id != null)
                {
                    Principal.identity().then(function (account)
                    {
                        User.get({login: account.login}, function (result) {
                            $scope.smsServiceAssign.user = result;
                            SmsServiceAssign.update($scope.smsServiceAssign, onSaveSuccess, onSaveError);
                        });
                    });
                }
                else
                {
                    Principal.identity().then(function (account)
                    {
                        User.get({login: account.login}, function (result) {
                            $scope.smsServiceAssign.user = result;
                            SmsServiceAssign.save($scope.smsServiceAssign, onSaveSuccess, onSaveError);
                        });
                    });
                }
            }

        };

        $scope.clear = function() {

        };
}]);
