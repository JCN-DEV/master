'use strict';

angular.module('stepApp')
    .controller('JpEmployeeTrainingDeleteController',
     ['$scope', '$modalInstance', 'entity', 'JpEmployeeTraining',
     function ($scope, $modalInstance, entity, JpEmployeeTraining) {

        $scope.training = entity;
        $scope.clear = function () {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            console.log("id :"+id);
            JpEmployeeTraining.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
