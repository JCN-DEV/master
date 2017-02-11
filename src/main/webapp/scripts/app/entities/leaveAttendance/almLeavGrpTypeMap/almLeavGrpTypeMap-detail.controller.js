'use strict';

angular.module('stepApp')
    .controller('AlmLeavGrpTypeMapDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'AlmLeavGrpTypeMap', 'AlmLeaveGroup', 'AlmLeaveType',
    function ($scope, $rootScope, $stateParams, entity, AlmLeavGrpTypeMap, AlmLeaveGroup, AlmLeaveType) {
        $scope.almLeavGrpTypeMap = entity;
        $scope.load = function (id) {
            AlmLeavGrpTypeMap.get({id: id}, function(result) {
                $scope.almLeavGrpTypeMap = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:almLeavGrpTypeMapUpdate', function(event, result) {
            $scope.almLeavGrpTypeMap = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
