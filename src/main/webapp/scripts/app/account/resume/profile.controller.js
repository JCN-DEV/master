'use strict';

angular.module('stepApp')
    .controller('ProfileController',
     ['$scope', 'Sessions', 'User', 'Employee,Reference', 'Principal', 'ParseLinks',
     function ($scope, Sessions, User, Employee,Reference, Principal, ParseLinks) {

        $scope.employee = {};
        $scope.employee.id = null;
        $scope.employee.user = {};

        Principal.identity().then(function (account) {
            $scope.settingsAccount = account;
            $scope.user = User.get({'login': $scope.settingsAccount.login});
        });


            Employee.get({id: 'my'}, function (result) {
                $scope.employee = result;
            });


        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:employeeUpdate', result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.employeeUpdate = function () {
            $scope.isSaving = true;
            if ($scope.employee.id != null) {
                Employee.update($scope.employee, onSaveSuccess, onSaveError);
            } else {
                $scope.employee.user.id = $scope.user.id;
                Employee.save($scope.employee, onSaveSuccess, onSaveError);
            }
        };

        $scope.references = function() {
            Reference.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.references = result;
                $scope.total = headers('x-total-count');
            });
        }

    }]);
