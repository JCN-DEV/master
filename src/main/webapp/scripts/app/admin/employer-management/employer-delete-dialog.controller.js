'use strict';

angular.module('stepApp')
    .controller('EmployerDeleteController',
    ['$scope', '$modalInstance', 'entity', 'TempEmployer',
    function ($scope, $modalInstance, entity, TempEmployer) {

        $scope.employer = entity;
        $scope.clear = function () {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            TempEmployer.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
