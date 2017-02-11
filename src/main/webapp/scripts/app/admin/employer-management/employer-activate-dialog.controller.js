'use strict';

angular.module('stepApp')
    .controller('EmployerActivateController',
    ['$scope', 'entity', '$modalInstance', 'User', 'Employer', 'TempEmployer', 'EmployerByUserId',
    function ($scope, entity, $modalInstance, User, Employer, TempEmployer, EmployerByUserId) {

        $scope.employer = entity;
        $scope.tempEmployer = {};
        $scope.clear = function () {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmActivate = function (id) {
            TempEmployer.get({id: id}, function (result) {
               /* $scope.tempEmployer = result;
                $scope.tempEmployer.user.langKey = "en";
                $scope.tempEmployer.user.activated = true;
                $scope.tempEmployer.user.authorities = ["ROLE_EMPLOYER"];
                User.update($scope.tempEmployer.user);*/
                $scope.tempEmployer = result;
                $scope.tempEmployer.status = 'approved';
                TempEmployer.update($scope.tempEmployer, afterUpdateTempEmployer);
            });
        };

        var afterUpdateTempEmployer = function (tempEmployer) {
            EmployerByUserId.get({id: tempEmployer.user.id}, function (employer) {
                    tempEmployer.status = null;
                    tempEmployer.id = employer.id;
                    Employer.update(tempEmployer, afterUpdateEmployer);
                },
                function (response) {
                    if (response.status === 404) {
                        tempEmployer.id = null;
                        tempEmployer.status = null;
                        Employer.save(tempEmployer, afterUpdateEmployer);
                    }
                }
            );
            $scope.$emit('stepApp:tempEmployerUpdate', tempEmployer);
        };

        var afterUpdateEmployer = function (result) {
            $scope.$emit('stepApp:EmployerUpdate', result);
            $modalInstance.close(true);
        };

    }]);
