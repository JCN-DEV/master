'use strict';

angular.module('stepApp')
    .controller('JpEmployeeReferenceDeleteController',
    ['$scope', '$modalInstance', 'entity', 'JpEmployeeReference',
    function ($scope, $modalInstance, entity, JpEmployeeReference) {

        $scope.reference = entity;
        $scope.clear = function () {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            console.log("id :"+id);
            JpEmployeeReference.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
