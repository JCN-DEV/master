'use strict';

angular.module('stepApp')
    .controller('AlmLeaveGroupDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'AlmLeaveGroup',
    function ($scope, $rootScope, $stateParams, entity, AlmLeaveGroup) {
        $scope.almLeaveGroup = entity;
        $scope.load = function (id) {
            AlmLeaveGroup.get({id: id}, function(result) {
                $scope.almLeaveGroup = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:almLeaveGroupUpdate', function(event, result) {
            $scope.almLeaveGroup = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
