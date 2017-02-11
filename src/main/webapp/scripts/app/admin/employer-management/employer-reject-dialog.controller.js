'use strict';
angular.module('stepApp')
    .controller('EmployerRejectController',
     ['$scope', '$modalInstance', 'entity', 'TempEmployer', 'Employer', 'EmployerByUserId',
     function ($scope, $modalInstance, entity, TempEmployer, Employer, EmployerByUserId) {

        $scope.tempEmployer = entity;

        $scope.clear = function () {
            $modalInstance.dismiss('cancel');
        };

        $scope.confirmReject = function (id) {

            //$scope.tempEmployer = {};

            /*TempEmployer.get({id: id}, function (result) {
                    $scope.tempEmployer = result;

                    if ($scope.tempEmployer.id != null) {

                        if($scope.tempEmployer.status == 'pending')
                            $scope.tempEmployer.status = 'rejected';
                        else if($scope.tempEmployer.status == 'update')
                            $scope.tempEmployer.status = 'updateReject';

                        TempEmployer.update($scope.tempEmployer, afterUpdateTempEmployer);
                    }
                }*/

            if (id != null) {
                $scope.tempEmployer.dateRejected = new Date();
                if($scope.tempEmployer.status == 'pending')
                    $scope.tempEmployer.status = 'rejected';
                else if($scope.tempEmployer.status == 'update')
                    $scope.tempEmployer.status = 'updateReject';

                TempEmployer.update($scope.tempEmployer, afterUpdateTempEmployer);
            };
        };

        var afterUpdateTempEmployer = function (tempEmployer) {
            $scope.$emit('stepApp:tempEmployerUpdate', tempEmployer);
            $modalInstance.close(true);
        };


    }]);
