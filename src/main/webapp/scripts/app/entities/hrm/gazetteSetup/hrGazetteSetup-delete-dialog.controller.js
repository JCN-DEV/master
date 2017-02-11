'use strict';

angular.module('stepApp')
	.controller('HrGazetteSetupDeleteController',
	 ['$scope', '$rootScope', '$modalInstance', 'entity', 'HrGazetteSetup',
	 function($scope, $rootScope, $modalInstance, entity, HrGazetteSetup) {

        $scope.hrGazetteSetup = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HrGazetteSetup.delete({id: id},
                function () {
                    $modalInstance.close(true);
                    $rootScope.setErrorMessage('stepApp.hrGazetteSetup.deleted');
                });
        };

    }]);
