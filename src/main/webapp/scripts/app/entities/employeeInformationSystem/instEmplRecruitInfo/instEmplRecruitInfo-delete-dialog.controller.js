'use strict';

angular.module('stepApp')
	.controller('InstEmplRecruitInfoDeleteController',
	['$scope', '$modalInstance', 'entity', 'InstEmplRecruitInfo',
	 function($scope, $modalInstance, entity, InstEmplRecruitInfo) {

        $scope.instEmplRecruitInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            InstEmplRecruitInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
            $rootScope.setErrorMessage('stepApp.InstEmplRecruitInfo.deleted');
        };

    }]);
