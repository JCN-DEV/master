'use strict';

angular.module('stepApp')
    .controller('StaffCountDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'StaffCount', 'Institute', 'User',
    function ($scope, $rootScope, $stateParams, entity, StaffCount, Institute, User) {
        $scope.staffCount = entity;
        $scope.load = function (id) {
            StaffCount.get({id: id}, function(result) {
                $scope.staffCount = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:staffCountUpdate', function(event, result) {
            $scope.staffCount = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
