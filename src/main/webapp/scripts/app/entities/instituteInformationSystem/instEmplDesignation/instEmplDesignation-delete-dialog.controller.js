'use strict';

angular.module('stepApp')
	.controller('InstEmplDesignationDeleteController',
    ['$scope', '$rootScope', '$modalInstance', 'entity', 'InstEmplDesignation',
    function($scope, $rootScope, $modalInstance, entity, InstEmplDesignation) {

        $scope.instEmplDesignation = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            InstEmplDesignation.delete({id: id},
                function () {
                    $modalInstance.close(true);
                   window.history.back();
                });
            $rootScope.setErrorMessage('stepApp.InstEmplDesignation.deleted');
        };

    }]);
