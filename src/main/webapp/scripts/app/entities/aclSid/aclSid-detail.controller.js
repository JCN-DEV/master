'use strict';

angular.module('stepApp')
    .controller('AclSidDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'AclSid',
    function ($scope, $rootScope, $stateParams, entity, AclSid) {
        $scope.aclSid = entity;
        $scope.load = function (id) {
            AclSid.get({id: id}, function(result) {
                $scope.aclSid = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:aclSidUpdate', function(event, result) {
            $scope.aclSid = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
