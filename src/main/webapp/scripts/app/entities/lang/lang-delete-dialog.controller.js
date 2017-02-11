'use strict';

angular.module('stepApp')
	.controller('LangDeleteController',
	['$scope', '$modalInstance', 'entity', 'Lang',
	function($scope, $modalInstance, entity, Lang) {

        $scope.lang = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Lang.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
