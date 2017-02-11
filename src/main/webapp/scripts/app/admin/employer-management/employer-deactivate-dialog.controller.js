'use strict';

angular.module('stepApp')
    .controller('EmployerDeactivateController',
    ['$scope', 'entity', '$modalInstance', 'User', 'Employer', 'TempEmployer',
    function ($scope, entity, $modalInstance, User, Employer, TempEmployer) {

        $scope.employer = entity;
        $scope.tempEmployer = {};
        $scope.clear = function () {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDeactivate = function (id) {
            TempEmployer.get({id: id}, function (result) {
                console.log(result);
                /*$scope.tempEmployer = result;
                $scope.tempEmployer.user.langKey = "en";
                $scope.tempEmployer.user.activated = false;
                $scope.tempEmployer.user.authorities = ["ROLE_EMPLOYER"];
                User.update($scope.tempEmployer.user);*/
                $scope.tempEmployer = result;
                $scope.tempEmployer.status = 'deActivated';
                TempEmployer.update($scope.tempEmployer, afterUpdateTempEmployer);
            });
        };

        var afterUpdateTempEmployer = function (tempEmployer) {
            $modalInstance.close(true);
        };

    }]);
