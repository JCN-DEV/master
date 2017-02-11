'use strict';

angular.module('stepApp')
    .controller('AclObjectIdentityDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'AclObjectIdentity', 'AclClass', 'AclSid',
    function ($scope, $rootScope, $stateParams, entity, AclObjectIdentity, AclClass, AclSid) {
        $scope.aclObjectIdentity = entity;
        $scope.load = function (id) {
            AclObjectIdentity.get({id: id}, function(result) {
                $scope.aclObjectIdentity = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:aclObjectIdentityUpdate', function(event, result) {
            $scope.aclObjectIdentity = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
