'use strict';

angular.module('stepApp')
	.controller('CmsTradeDeleteController',
        ['$scope', '$modalInstance', 'entity', 'CmsTrade',
        function($scope, $modalInstance, entity, CmsTrade) {

        $scope.cmsTrade = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            CmsTrade.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
