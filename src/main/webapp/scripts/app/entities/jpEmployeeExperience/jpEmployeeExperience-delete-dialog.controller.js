'use strict';

angular.module('stepApp')
    .controller('JpEmployeeExperienceDeleteController',
    ['$scope', '$modalInstance', 'entity', 'JpEmployeeExperience',
    function ($scope, $modalInstance, entity, JpEmployeeExperience) {

        $scope.skill = entity;
        $scope.clear = function () {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            console.log("id :"+id);
            JpEmployeeExperience.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
