'use strict';

angular.module('stepApp')
    .controller('JpAcademicQualificationrDeleteController',
     ['$scope', '$modalInstance', 'entity', 'JpAcademicQualification',
     function ($scope, $modalInstance, entity, JpAcademicQualification) {

        $scope.jpAcademicQualification = entity;
        $scope.clear = function () {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            console.log("id :"+id);
            JpAcademicQualification.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
