'use strict';

angular.module('stepApp')
    .controller('UmracRoleAssignUserDetailController',
        ['$scope', '$rootScope', '$stateParams', 'entity', 'UmracRoleAssignUser', 'UmracIdentitySetup', 'UmracRoleSetup',
        function ($scope, $rootScope, $stateParams, entity, UmracRoleAssignUser, UmracIdentitySetup, UmracRoleSetup) {
        $scope.umracRoleAssignUser = entity;
        $scope.load = function (id) {
            UmracRoleAssignUser.get({id: id}, function(result) {
                $scope.umracRoleAssignUser = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:umracRoleAssignUserUpdate', function(event, result) {
            $scope.umracRoleAssignUser = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
