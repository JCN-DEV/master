'use strict';

angular.module('stepApp')
    .controller('AssetRepairDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'AssetRepair', 'Employee', 'AssetRecord',
    function ($scope, $rootScope, $stateParams, entity, AssetRepair, Employee, AssetRecord) {
        $scope.assetRepair = entity;
        $scope.load = function (id) {
            AssetRepair.get({id: id}, function(result) {
                $scope.assetRepair = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:assetRepairUpdate', function(event, result) {
            $scope.assetRepair = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
