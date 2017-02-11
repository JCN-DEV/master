'use strict';

angular.module('stepApp')
    .controller('JpEmploymentHIstoryDeleteController',
    ['$scope', '$modalInstance', 'entity', 'JpEmploymentHistory',
    function ($scope, $modalInstance, entity, JpEmploymentHistory) {

        $scope.employment = entity;
        $scope.clear = function () {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            console.log("id :"+id);
            JpEmploymentHistory.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
