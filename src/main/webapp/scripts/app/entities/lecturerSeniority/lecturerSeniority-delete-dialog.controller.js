'use strict';

angular.module('stepApp')
	.controller('LecturerSeniorityDeleteController',
    ['$scope','$modalInstance','entity','LecturerSeniority',
    function($scope, $modalInstance, entity, LecturerSeniority) {

        $scope.lecturerSeniority = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            LecturerSeniority.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
