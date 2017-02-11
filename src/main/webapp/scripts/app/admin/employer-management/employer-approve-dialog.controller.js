'use strict';

angular.module('stepApp')
    .controller('EmployerApproveController',
     ['$scope', '$modalInstance', 'entity', 'User', 'TempEmployer', 'Employer', 'EmployerByUserId',
     function ($scope, $modalInstance, entity, User, TempEmployer, Employer, EmployerByUserId) {

        $scope.employer = entity;
        $scope.isSaving = false;
        console.log($scope.employer);
        $scope.clear = function () {
            $modalInstance.dismiss('cancel');
        };

        $scope.confirmApprove = function (id) {
            $scope.isSaving = true;
            $scope.tempEmployer = {};

            TempEmployer.get({id: id}, function (result) {
                    console.log('print result :'+id);
                    $scope.tempEmployer = result;
                    console.log($scope.tempEmployer);
                    if ($scope.tempEmployer.id != null) {
                        $scope.tempEmployer.status = 'approved';
                        //$scope.tempEmployer.user.langKey = "en";
                        //$scope.tempEmployer.user.activated = true;
                       // $scope.tempEmployer.user.authorities = ["ROLE_EMPLOYER"];
                        console.log($scope.tempEmployer.user);
                        //User.update($scope.tempEmployer.user);
                        TempEmployer.update($scope.tempEmployer, afterUpdateTempEmployer);
                    }
                }
            );
        };

        var afterUpdateTempEmployer = function (tempEmployer) {
            EmployerByUserId.get({id: tempEmployer.user.id}, function (employer) {
                    tempEmployer.status = null;
                    tempEmployer.id = employer.id;
                    tempEmployer.dateApproved = new Date();
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
            $scope.isSaving = false;
        };

    }]);
