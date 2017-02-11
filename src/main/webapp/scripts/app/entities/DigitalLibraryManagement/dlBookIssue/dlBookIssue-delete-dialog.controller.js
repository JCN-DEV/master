'use strict';

angular.module('stepApp')
	.controller('DlBookIssueDeleteController',
	['$scope','$rootScope','$state', '$modalInstance', 'entity', 'DlBookIssue',
	function($scope,$rootScope,$state, $modalInstance, entity, DlBookIssue) {

        $scope.dlBookIssue = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            $state.go('libraryInfo.dlBookIssue', null, { reload: true });
            DlBookIssue.delete({id: id},
                function () {
                    $modalInstance.close(true);
                    $rootScope.setErrorMessage('stepApp.dlBookIssue.deleted');
                });

        };

    }]);
