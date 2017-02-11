'use strict';

angular.module('stepApp')
    .controller('AclClassDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'AclClass',
     function ($scope, $rootScope, $stateParams, entity, AclClass) {
        $scope.aclClass = entity;
        $scope.load = function (id) {
            AclClass.get({id: id}, function(result) {
                $scope.aclClass = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:aclClassUpdate', function(event, result) {
            $scope.aclClass = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
