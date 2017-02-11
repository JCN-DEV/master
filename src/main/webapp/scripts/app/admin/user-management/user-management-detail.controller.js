'use strict';

angular.module('stepApp')
    .controller('UserManagementDetailController',
    ['$scope', '$stateParams', 'User',
    function ($scope, $stateParams, User) {
        $scope.user = {};
        $scope.load = function (login) {
            User.get({login: login}, function(result) {
                $scope.user = result;
            });
        };
        $scope.load($stateParams.login);
    }]);
