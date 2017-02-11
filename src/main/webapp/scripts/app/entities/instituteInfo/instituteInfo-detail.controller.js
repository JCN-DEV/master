'use strict';

angular.module('stepApp')
    .controller('InstituteInfoDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'InstituteInfo', 'User',
    function ($scope, $rootScope, $stateParams, entity, InstituteInfo, User) {
        $scope.instituteInfo = entity;
        $scope.load = function (id) {
            InstituteInfo.get({id: id}, function(result) {
                $scope.instituteInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instituteInfoUpdate', function(event, result) {
            $scope.instituteInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
