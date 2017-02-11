'use strict';

angular.module('stepApp')
	.controller('DlBookIssueDeleteController',
	['$scope', '$modalInstance', 'entity', 'DlBookIssue',
	 function($scope, $modalInstance, entity, DlBookIssue) {

        $scope.dlBookIssue = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            DlBookIssue.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
