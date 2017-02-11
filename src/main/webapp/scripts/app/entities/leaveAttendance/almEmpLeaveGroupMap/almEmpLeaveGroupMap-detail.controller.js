'use strict';

angular.module('stepApp')
    .controller('AlmEmpLeaveGroupMapDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'AlmEmpLeaveGroupMap', 'HrEmployeeInfo', 'AlmLeaveGroup',
    function ($scope, $rootScope, $stateParams, entity, AlmEmpLeaveGroupMap, HrEmployeeInfo, AlmLeaveGroup) {
        $scope.almEmpLeaveGroupMap = entity;
        $scope.load = function (id) {
            AlmEmpLeaveGroupMap.get({id: id}, function(result) {
                $scope.almEmpLeaveGroupMap = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:almEmpLeaveGroupMapUpdate', function(event, result) {
            $scope.almEmpLeaveGroupMap = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
