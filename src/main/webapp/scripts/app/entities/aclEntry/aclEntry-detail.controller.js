'use strict';

angular.module('stepApp')
    .controller('AclEntryDetailController',
     ['$scope', '$rootScope', '$stateParams', 'entity', 'AclEntry', 'AclObjectIdentity', 'AclSid',
    function ($scope, $rootScope, $stateParams, entity, AclEntry, AclObjectIdentity, AclSid) {
        $scope.aclEntry = entity;
        $scope.load = function (id) {
            AclEntry.get({id: id}, function(result) {
                $scope.aclEntry = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:aclEntryUpdate', function(event, result) {
            $scope.aclEntry = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
